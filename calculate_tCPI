import qupath.lib.objects.PathObjects
import qupath.lib.roi.ROIs
import qupath.lib.roi.ConvexHull
import qupath.lib.regions.ImagePlane
import qupath.lib.objects.classes.PathClassFactory

def allSelected = getSelectedObjects()

println("Total selected objects: ${allSelected.size()}")

allSelected.each { annotation ->
    def childDetections = annotation.getChildObjects().findAll { it.isDetection() }
    if (!childDetections || childDetections.size() == 0) {
        return
    }

    println("Processing lesion annotation with ${childDetections.size()} detections.")

    // Sort the detections based on 'Signed distance to annotation lesion µm' in descending order
    childDetections.sort { b, a ->
        def aValue = a.getMeasurementList().getMeasurementValue("Signed distance to annotation lesion µm")
        def bValue = b.getMeasurementList().getMeasurementValue("Signed distance to annotation lesion µm")
        return aValue <=> bValue
    }

    // Take the second 50% of detections for this lesion
    int halfSize = (int)(childDetections.size() * 0.5)
    def detectionsList = childDetections.subList(halfSize, childDetections.size())

    println("Selected ${detectionsList.size()} detections for this lesion.")

    // Create a convex hull for the selected detections in this lesion
    def points = detectionsList.collectMany { it.getROI().getAllPoints() }
    if (points.size() > 0) {
        def hullPoints = ConvexHull.getConvexHull(points)
        def roi = ROIs.createPolygonROI(hullPoints, ImagePlane.getDefaultPlane())
        def hullAnnotation = PathObjects.createAnnotationObject(roi)

        // Set the class of the hullAnnotation to "inner convex"
        def innerConvexClass = PathClassFactory.getPathClass("inner convex")
        hullAnnotation.setPathClass(innerConvexClass)

        // Name the convex hull annotation based on the parent lesion
        hullAnnotation.setName("${annotation.getName()} - Convex Hull")

        addObject(hullAnnotation)
        
        // Access the measurements directly
        def lesionArea = annotation.getROI().getArea()
        def convexArea = hullAnnotation.getROI().getArea()

        println("Lesion Area: ${lesionArea}, Convex Hull Area: ${convexArea}")
        
        if (lesionArea && convexArea && lesionArea != 0) {
            def ratio = 1 - (convexArea / lesionArea)
            // Add the measurement to the original lesion annotation
            annotation.getMeasurementList().putMeasurement("tCPI", ratio)
} else {
    println("Warning: Unable to calculate ratio for ${annotation.getName()}. Check the measurements.")
}

        
        // Remove the convex hull annotation after computing the measurements
        getCurrentImageData().getHierarchy().removeObject(hullAnnotation, true)

    }
}

println("Operation completed!")
