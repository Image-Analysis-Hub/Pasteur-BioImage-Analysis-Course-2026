// Author: Julie Mabon
// Usage : Exports a ROI with all annotations inside as patch + masks in a cellpsoe compatible format
// Annotate objects in an image
// draw a rectangle as the ROI to export and keep it selected
// Keep it selected and run the script
// Results are sent to `export` in your QuPath project
// You can load these in cellpose gui by checking `Autoload masks from _masks.tif files` before loading the image.
import qupath.lib.images.servers.LabeledImageServer
import qupath.lib.objects.PathObjectTools
def imageData = getCurrentImageData()
def project = getProject()
def server = imageData.getServer()

def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'export')
mkdirs(pathOutput)

def exportName = Dialogs.showInputDialog("Export name", "enter name", "region")

def downsample = 1.0


def labelServer = new LabeledImageServer.Builder(imageData)
    .backgroundLabel(0, ColorTools.BLACK)
    .downsample(downsample) 
    .multichannelOutput(false)
    .useInstanceLabels()
    .useFilter(o -> o.getPathClass() == null) // Keep only objects with no PathClass
    .build()


annotation = getSelectedObject()

//Export the mask and image
def region = RegionRequest.createInstance(labelServer.getPath(), downsample, annotation.getROI())
def outputPathLabel = buildFilePath(pathOutput, exportName + '_masks.tif')
writeImageRegion(labelServer, region, outputPathLabel)

def outputPathimage = buildFilePath(pathOutput, exportName + '.tif')
writeImageRegion(server, region, outputPathimage)

print "saved" + outputPathLabel
print "saved" + outputPathimage
