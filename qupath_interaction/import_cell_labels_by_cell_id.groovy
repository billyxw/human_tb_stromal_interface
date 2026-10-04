// Import the required QuPath classes
import qupath.lib.objects.PathObjects
import qupath.lib.objects.classes.PathClass

// Path to the CSV file
def path = "C:/Users/wxia5049/Desktop/Test csv/04972_leiden.csv" // Update this to the path of your CSV\

// Color separator
def delim = ","

// Get a map from cell ID -> cell
def cells = getCellObjects()
def cellsById = cells.groupBy { it.getID().toString() }

// Read lines from the CSV
def lines = new File(path).readLines()
def header = lines[0].split(delim) // Get the header without removing it

// Handle each line in the CSV (starting from the second line to skip the header)
for (line in lines.drop(1)) {
    def map = lineToMap(header, line.split(delim))
    def id = map['object_id'] // Use the correct column name from your CSV
    def cell = cellsById[id]
    if (cell == null || cell.isEmpty()) {
        println "WARN: No cell found for ID $id"
        continue
    } else if (cell.size() > 1) {
        println "WARN: ${cell.size()} cells found for ID $id - will skip"
        continue
    }
    // Set the classification for the cell using PathClass.fromArray
    def classification = map['leiden_0.4']
    if (classification) {
        def pathClass = PathClass.fromArray(classification)
        cell[0].setPathClass(pathClass)
    } else {
        println "WARN: No classification found for ID $id"
    }
}

// Helper function to create a map from column headings -> values
Map lineToMap(String[] header, String[] content) {
    def map = [:]
    if (header.size() != content.size()) {
        throw new IllegalArgumentException("Header length doesn't match the content length!")
    }
    for (int i = 0; i < header.size(); i++) {
        map[header[i]] = content[i]
    }
    return map
}
