# A stromal interface links distinct immune programs across the human tuberculosis lung

This repository contains study-specific analysis code associated with the manuscript:

**A stromal interface links distinct immune programs across the human tuberculosis lung**

Most analyses in this study were performed using established open-source Python and R packages, including Scanpy, Squidpy, LIANA+, GSEApy, scvi-tools, SPACEc and spicyR.

This repository provides custom scripts and analysis code developed for study-specific processing, visualization and downstream analyses.

## Included scripts

### QuPath interaction

Custom Groovy scripts are provided for lesion-level spatial-index calculation and spatial validation of clustering results in QuPath.

These scripts enable automated calculation of spatial indices across individual lesion annotations, map cell-level annotations including Leiden cluster assignments back to segmented cells using unique cell identifiers, and support direct visualization and validation of clustering results within the original tissue sections.

### SPACEc analysis
Custom Python notebooks are provided for tissue-context mapping and spatial interaction analysis using SPACEc.

These workflows construct local tissue-context maps, visualize relationships among selected spatial domains, and quantify pariwise domain interactions across multiple samples.

### Spatial transriptomic analysis
Custom Python notebooks are provided for downstream analysis of Visium HD spatial transcriptomic data.

These wrokflows include boundary-resolved transcriptional analysis across the selected histology annotaed region interface, regional characterization of NMF-derived spatial ligand-receptor programs.

## Data and software

Public datasets and software packages used in the study are described in the manuscript Methods.

