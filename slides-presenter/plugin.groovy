import com.intellij.codeInsight.completion.*
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.JBColor
import org.jetbrains.annotations.NotNull

import javax.swing.*

import static liveplugin.PluginUtil.*
import static liveplugin.PluginUtil.show

// Mini-plugin with couple actions to have slides in IJ.
// To run it use https://github.com/dkandalov/live-plugin


if (isIdeStartup) return

def slidePath = { "slides/slides.${String.format("%03d", it)}.png" }
def pngSlides = (1..100).collect { slidePath(it) }
def takeSlides = { amount ->
	(0..<amount).collect { pngSlides.remove(0) }
}

def slides = [
	takeSlides(8),
	"src/0-coroutines/0.lua",
	"src/0-coroutines/0_.lua",
	takeSlides(7),
	"src/0-coroutines/0__.lua",
	takeSlides(10),
	"src/0-coroutines/1.lua",
	"src/0-coroutines/2.lua",
	"src/0-coroutines/2_.lua",
	takeSlides(2),
	"src/0-coroutines/3.lua",
	takeSlides(12),
	"src/0-coroutines/4.lua",
	takeSlides(1),
	"src/0-coroutines/5.js",
	"src/0-coroutines/5_.js",
	"src/0-coroutines/6.js",
	"src/0-coroutines/6_.js",
	takeSlides(10),
	"src/1-cps/groovy/_0_hello.groovy",
	"src/1-cps/groovy/_0_hello_.groovy",
	"src/1-cps/groovy/_1_id.groovy",
	"src/1-cps/groovy/_1_id_.groovy",
	"src/1-cps/groovy/_2_factorial.groovy",
	"src/1-cps/groovy/_2_factorial_.groovy",
	"src/1-cps/groovy/_3.groovy",
	"src/1-cps/groovy/_4.groovy",
	takeSlides(1),
	"src/2-callcc/0-hello.scm",
	"src/2-callcc/1-return.scm",
	"src/2-callcc/2-saved-return.scm",
	"src/2-callcc/3-yield.scm",
	"src/2-callcc/5-multitasking.scm",
	takeSlides(18),
/*
	"src/3-generators/kotlin/0-basic-yield.kts",
	"src/3-generators/kotlin/0-basic-yield-value.kts",
	"src/3-generators/kotlin/1-0-yield.kts",
	"src/3-generators/kotlin/1-1-yield-loop.kts",
	"src/3-generators/kotlin/1-2-yield-finally.kts",
	"src/3-generators/kotlin/2-factorial.kts",
*/
	"src/3-generators/0-yield.js",
	"src/3-generators/0-yield-1.js",
	"src/3-generators/0-yield-2.js",
	"src/3-generators/0-yield-2-loop.js",
	"src/3-generators/0-yield-3-loop.js",
	"src/3-generators/0-yield-6-catch.js",
	"src/3-generators/0-yield-6-finally.js",
	"src/3-generators/1-yield-factorial.js",
	"src/3-generators/1-yield-factorial-2.js",
	"src/3-generators/1b-yield-factorial.js",
	"src/3-generators/2-yield.cs",
	"src/3-generators/2-yield-fibonacci.cs",
	takeSlides(1),
	"src/4-pull-push/0.kts",
	takeSlides(11),
	"src/4-pull-push/1-iterable-observable.kts",
	takeSlides(3),
	"src/5-async-await/0-0-blocking-read.js",
	"src/5-async-await/0-1-callback-read.js",
	"src/5-async-await/0-2-promise-read.js",
	"src/5-async-await/0-3-async-read.js",
	"src/5-async-await/0-3b-async-read.js",
	"src/5-async-await/1.js",
	takeSlides(12),
].flatten()

def slidesBasePath = "/Users/dima/IdeaProjects/coroutines-explained/"
def currentSlide = -1
def openCurrentSlide = { event ->
	def file = slidesBasePath + slides[currentSlide]
	def openedFile = openInEditor(file, event.project)
	if (openedFile == null) show("Slide not found ${file}")
}

registerAction("PreviousSlide", "F11") { event ->
	currentSlide--
	if (currentSlide < 0) currentSlide = 0
	openCurrentSlide(event)
}
registerAction("Previous10Slides", "alt F11") { event ->
	currentSlide -= 10
	if (currentSlide < 0) currentSlide = 0
	openCurrentSlide(event)
}
registerAction("NextSlide", "F12") { event ->
	currentSlide++
	if (currentSlide >= slides.size()) currentSlide = slides.size() - 1
	openCurrentSlide(event)
}
registerAction("Next10Slides", "alt F12") { event ->
	currentSlide += 10
	if (currentSlide >= slides.size()) currentSlide = slides.size() - 1
	openCurrentSlide(event)
}

registerEditorListener(pluginDisposable, new FileEditorManagerListener() {
	@Override void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
		def editor = source?.getSelectedEditor(file)
		if (editor?.component?.class?.name?.contains("ImageEditorUI")) {
			def ui = editor?.component
			ui.remove(0)
			ui.zoomModel.setZoomFactor(1.1d)
			def scrollPane = accessField(ui, ["g", "myScrollPane"], JScrollPane.class)
			scrollPane.viewport.background = JBColor.white
			scrollPane.viewport.repaint()
		}

		def relativePath = file.canonicalPath.replace(slidesBasePath, "")
		def i = slides.indexOf(relativePath)
		if (i != -1) currentSlide = i
	}
})

show("Reloaded slides presenter actions")