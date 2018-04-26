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
def pngSlides = (1..200).collect { slidePath(it) }
def takeSlides = { amount ->
	(0..<amount).collect { pngSlides.remove(0) }
}

def slides = [
	takeSlides(7),
	
	// Coroutines as threads
	takeSlides(2),
	takeSlides(7),
	"src/0-coroutines-as-threads/0-subroutine.lua",
	takeSlides(12),
	"src/0-coroutines-as-threads/1.lua",
	"src/0-coroutines-as-threads/2.lua",
	"src/0-coroutines-as-threads/2_.lua",
	takeSlides(2),
	"src/0-coroutines-as-threads/3-c1c2.lua",
	takeSlides(14),
	"src/0-coroutines-as-threads/4-sub-yield.lua",
	takeSlides(3),

	// Coroutines as yield
	takeSlides(5),
	"src/1-coroutines-yield/0-yield-0.js",
	"src/1-coroutines-yield/0-yield-1.js",
	"src/1-coroutines-yield/0-yield-2.js",
	"src/1-coroutines-yield/0-yield-2-loop.js",
	"src/1-coroutines-yield/0-yield-3-loop.js",
	"src/1-coroutines-yield/0-yield-6-finally.js",
	"src/1-coroutines-yield/1-yield-factorial.js",
	"src/1-coroutines-yield/1-yield-factorial-2.js",
	"src/1-coroutines-yield/1b-yield-factorial.js",
	"src/1-coroutines-yield/cpp/0-yield.cpp",
	"src/1-coroutines-yield/cpp/1-yield.cpp",
	takeSlides(12),

	// Coroutines as async/await
	"src/2-coroutines-as-asyncawait/0.js",
	"src/2-coroutines-as-asyncawait/1.js",
	"src/2-coroutines-as-asyncawait/2.js",
	"src/2-coroutines-as-asyncawait/2b.js",
	"src/2-coroutines-as-asyncawait/cpp/0-async-await.cpp",
	takeSlides(2),

	// coroutines as call/cc
	takeSlides(3),
	"src/3-coroutines-as-callcc/0-cps/groovy/_0_hello.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_0_hello_.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_1_id.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_1_id_.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_2_factorial.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_2_factorial_.groovy",
	"src/3-coroutines-as-callcc/0-cps/groovy/_3.groovy",
	takeSlides(1),
	"src/3-coroutines-as-callcc/1-callcc/0-hello.scm",
	"src/3-coroutines-as-callcc/1-callcc/1-return.scm",
	"src/3-coroutines-as-callcc/1-callcc/2-saved-continuation.scm",
	takeSlides(11),
	"src/3-coroutines-as-callcc/1-callcc/3-yield.scm",
	"src/3-coroutines-as-callcc/2-boost-callcc/callcc.cpp",
	"src/3-coroutines-as-callcc/2-boost-callcc/callcc-factorial.cpp",
	"src/4-generators/kotlin/0-basic-yield.kts",
	takeSlides(4),

	// Coroutines design flavours comparison
	takeSlides(6),

	// Real world generators examples
	takeSlides(1),
	"src/4-generators/0-date-sequence.kts",
	"src/4-generators/1-database-stream.kts",
	takeSlides(1),

	// Callback hell
	takeSlides(1),
	"src/5-callback-hell/0-0-blocking-read.js",
	"src/5-callback-hell/0-1-callback-read.js",
	"src/5-callback-hell/0-2-promise-read.js",
	"src/5-callback-hell/0-3-async-read.js",
	takeSlides(3),
	"src/5-callback-hell/kotlin/1-iterable-observable.kts",
	takeSlides(2),

	// Monads in imperative language
	takeSlides(1),
	"src/6-async-vs-promises/0-async-while.js",
	"src/6-async-vs-promises/1-promise-while.js",
	"src/6-async-vs-promises/2-async-retry.js",
	"src/6-async-vs-promises/3-promise-retry.js",
	takeSlides(2),

	// Summary
	takeSlides(8),

	"src/5-callback-hell/kotlin/x-snake.kts"
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