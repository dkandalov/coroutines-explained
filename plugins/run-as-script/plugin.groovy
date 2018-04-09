import com.intellij.execution.ExecutionManager
import com.intellij.execution.Executor
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.execution.ui.ExecutionConsole
import com.intellij.execution.ui.RunContentDescriptor
import com.intellij.execution.ui.actions.CloseAction
import com.intellij.icons.AllIcons
import com.intellij.notification.*
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.progress.PerformInBackgroundOption
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import liveplugin.implementation.Misc

import javax.swing.*
import java.awt.*
import java.util.concurrent.atomic.AtomicReference

import static com.intellij.openapi.util.io.FileUtil.*
import static liveplugin.PluginUtil.*

def cleanupActions = []

def showInConsole2 = { message, project ->
	String consoleTitle = ""
	ConsoleViewContentType contentType = guessContentTypeOf(message)

	AtomicReference<ConsoleView> result = new AtomicReference(null)
	// Use reference for consoleTitle because get groovy Reference class like in this bug http://jira.codehaus.org/browse/GROOVY-5101
	AtomicReference<String> titleRef = new AtomicReference(consoleTitle)

	invokeOnEDT {
		cleanupActions.each { it.actionPerformed(anActionEvent()) }
		cleanupActions.clear()

		ConsoleView console = TextConsoleBuilderFactory.instance.createBuilder(project).console
		console.print(Misc.asString(message), contentType)

		DefaultActionGroup toolbarActions = new DefaultActionGroup()
		def consoleComponent = new MyConsolePanel(console, toolbarActions)
		RunContentDescriptor descriptor = new RunContentDescriptor(console, null, consoleComponent, titleRef.get()) {
			@Override boolean isContentReuseProhibited() { true }
			@Override Icon getIcon() { AllIcons.Nodes.Plugin }
		}
		Executor executor = DefaultRunExecutor.runExecutorInstance

		def closeAction = new CloseAction(executor, descriptor, project)
		cleanupActions.add(closeAction)
		toolbarActions.add(closeAction)
		console.createConsoleActions().each{ toolbarActions.add(it) }

		ExecutionManager.getInstance(project).contentManager.showRunContent(executor, descriptor)
		result.set(console)
	}
	result.get()
}

def runBash = { command, project ->
	def result = bash(command)
	def output = "\$ " + command + "\n" + result.stdout + "\n" + result.stderr
	output = output.replaceAll("/Users/dima/IdeaProjects/", "")
	showInConsole2(output, project)
	result
}



registerAction("CompileHaskell", "alt R", { AnActionEvent event ->
	def project = event.project
	VirtualFile file = currentFileIn(project)

	def docManager = FileDocumentManager.instance
	def document = docManager.getDocument(file)
	if (document != null) docManager.saveDocument(document)

	if (file.extension == "hs") {
		doInBackground("Compiling Haskell script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				def srcPath = findParent("src", file)
				def filePath = file.path.substring(srcPath.size() + 1, file.path.size())
				def result = runBash("cd ${srcPath} && /usr/local/bin/ghc --make ${filePath}", project)
				if (result.exitCode != 0) return
				runBash(srcPath + "/" + FileUtil.getNameWithoutExtension(filePath), project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "cs") {
		doInBackground("Compiling C# script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				def srcPath = file.parent.path
				def filePath = file.path.substring(srcPath.size() + 1, file.path.size())
				runBash("cd ${srcPath} && /usr/local/bin/csc ${filePath} && /usr/local/Cellar/mono/5.4.1.6/bin/mono " + srcPath + "/" + FileUtil.getNameWithoutExtension(filePath) + ".exe", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "lua") {
		doInBackground("Running Lua script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				runBash("/usr/local/bin/lua ${file.path}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "scm") {
		doInBackground("Running Scheme script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				runBash("/usr/local/Cellar/chicken/4.13.0/bin/csi -ss ${file.path}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "py") {
		doInBackground("Running Python script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				runBash("/usr/local/bin/python3 ${file.path}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "js") {
		doInBackground("Running JS script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				runBash("/usr/local/bin/node ${file.path}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}

	if (file.extension == "cpp" && file.parent.children.find { it.name == "cmake-build-debug"}) {
		doInBackground("Running CMake", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				def cmake = "/usr/local/Cellar/cmake/3.10.2/bin/cmake"
				def cmakeDebugDir = "${file.parent.path}/cmake-build-debug"
				runBash("$cmake --build ${cmakeDebugDir} --target ${file.name} -- -j 4 && ${cmakeDebugDir}/${file.name}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})

	}

	if (file.extension == "cpp") {
		// 	/Users/dima/Downloads/clang+llvm-6.0.0-x86_64-apple-darwin/bin/clang++ 0-yield.cpp -std=c++2a -fcoroutines-ts -stdlib=libc++ -o 0-yield && ./0-yield
		doInBackground("Running Clang", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				def clang = "/Users/dima/Downloads/clang+llvm-6.0.0-x86_64-apple-darwin/bin/clang++"
				runBash("$clang ${file.path} -std=c++2a -fcoroutines-ts -stdlib=libc++ -o ${file.nameWithoutExtension} && ./${file.nameWithoutExtension}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}
/*
	if (file.extension == "kts") {
		doInBackground("Running Kotlin script", false, PerformInBackgroundOption.ALWAYS_BACKGROUND, {
			try {
				runBash("/usr/local/Cellar/chicken/4.13.0/bin/csi -ss ${file.path}", project)
			} catch (Exception e) {
				showInConsole(e, project)
			}
		})
	}
*/
})

if (!isIdeStartup) show("Loaded run file action")

def findParent(folderName, file) {
	if (file.path.endsWith("/src")) file.path
	else findParent(folderName, file.parent)
}

def bash(command) {
	execute("bash", ["-c", "\"" + command + "\""])
}

static ConsoleViewContentType guessContentTypeOf(text) {
	text instanceof Throwable ? ConsoleViewContentType.ERROR_OUTPUT : ConsoleViewContentType.NORMAL_OUTPUT
}

class MyConsolePanel extends JPanel {
	MyConsolePanel(ExecutionConsole consoleView, ActionGroup toolbarActions) {
		super(new BorderLayout())
		def toolbarPanel = new JPanel(new BorderLayout())
		toolbarPanel.add(ActionManager.instance.createActionToolbar(ActionPlaces.UNKNOWN, toolbarActions, false).component)
		add(toolbarPanel, BorderLayout.WEST)
		add(consoleView.component, BorderLayout.CENTER)
	}
}
