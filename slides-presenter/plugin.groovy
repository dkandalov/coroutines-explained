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

def slides = [
	(1..31).collect { "slides/slides.${String.format("%03d", it)}.png" },
	"src/0-coroutines/0-hello.lua",
	"src/0-coroutines/1.lua",
	"src/0-coroutines/2.lua",
	"src/0-coroutines/3.lua",
	"src/0-coroutines/4.lua",
	(32..36).collect { "slides/slides.${String.format("%03d", it)}.png" },
	"src/1-cps/groovy/_0.groovy",
	"src/1-cps/groovy/_1.groovy",
	"src/1-cps/groovy/_2.groovy",
	"src/1-cps/groovy/_3.groovy",
	"src/1-cps/groovy/_4.groovy",
	(37..42).collect { "slides/slides.${String.format("%03d", it)}.png" },
	"src/2-callcc/0-hello.scm",
	"src/2-callcc/1-return.scm",
	"src/2-callcc/2-saved-return.scm",
	"src/2-callcc/3-yield.scm",
	"src/2-callcc/5-multitasking.scm",
].flatten()

def slidesBasePath = "/Users/dima/IdeaProjects/coroutines-explained/"
def currentSlide = -1

registerAction("PreviousSlide", "F11") { event ->
	currentSlide--
	if (currentSlide < 0) currentSlide = 0
	def file = slidesBasePath + slides[currentSlide]
	openInEditor(file, event.project)
}
registerAction("NextSlide", "F12") { event ->
	currentSlide++
	if (currentSlide > slides.size()) currentSlide = slides.size() - 1
	def file = slidesBasePath + slides[currentSlide]
	openInEditor(file, event.project)
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
	}
})

show("Reloaded slides presenter actions")