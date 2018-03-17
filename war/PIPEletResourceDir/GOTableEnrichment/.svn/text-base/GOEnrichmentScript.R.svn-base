require(GOstats)
#require(GO.db)


go.enrichment <- function (genes, ontology, annotationToUse, pvalue, geneUniverse=character (0)){
	   params = new ("GOHyperGParams", geneIds = unique (genes),
					 universeGeneIds = geneUniverse, annotation = annotationToUse,
					 ontology = ontology, pvalueCutoff = pvalue, conditional = FALSE,
					 testDirection = "over")

	   hgr = hyperGTest (params)
	   return (hgr)
	}

tabular.go.enrichment = function (hgr){
   tbl = summary (hgr)
   tbl = tbl [, c (7, 1, 5, 4, 6, 2)]

   rownames (tbl) = NULL

   if(ontologyType == "CC"){
        sample.row =  list (Term='bogus', GOCCID='bogus', Count=0, ExpCount=0, Size=0, Pvalue=0.0, goDepth=3, geneIDs='bogus')
   }else if (ontologyType == "MF"){
		sample.row =  list (Term='bogus', GOMFID='bogus', Count=0, ExpCount=0, Size=0, Pvalue=0.0, goDepth=3, geneIDs='bogus')
	}else{
        sample.row =  list (Term='bogus', GOBPID='bogus', Count=0, ExpCount=0, Size=0, Pvalue=0.0, goDepth=3, geneIDs='bogus')
	}
   df = data.frame (sample.row, stringsAsFactors=FALSE)

   virgin.tbl = TRUE
   for (r in 1:nrow (tbl)) {
        goTerm = tbl [r, 2]
        allGeneIDs = geneIdsByCategory (hgr) [[goTerm]]
        depth = getGoDepth(goTerm, ontologyType);
        newGeneList = ""
        for (gene in allGeneIDs) {
            if(newGeneList == ""){
                newGeneList = gene
            }else{
				newGeneList = paste(newGeneList, gene)
			}
        } # for gene
        old.row = tbl [r,]
        rownames (old.row) = NULL
        new.row = cbind (old.row,goDepth=depth, stringsAsFactors=FALSE)
        new.row = cbind (new.row,geneIDs=newGeneList, stringsAsFactors=FALSE)
        if (virgin.tbl) {
            df [1,] = new.row
            virgin.tbl = FALSE
        }else{
            df = rbind (df, new.row)
        }
    } # for r
	return(df)
}

readOrganism = function(filename){
    scannedFile = scan (filename, what=character(0), sep='\n')
 	return (scannedFile[1])
}
readOntologyType = function(filename){
    scannedFile = scan (filename, what=character(0), sep='\n')
 	return (scannedFile[2])
}
readOutFilename = function(filename){
    scannedFile = scan (filename, what=character(0), sep='\n')
 	return (scannedFile[3])
}
readGenes = function (filename){
  scannedFile = scan (filename, what=character(0), sep='\n')
  genes = c()
  for (i in 4: length (scannedFile))
      genes[i-3] = scannedFile[i]
  return (genes)
}
write.error.file = function(msg){
  errorFile = file ('ERROR', 'w')
  cat (msg, file=errorFile)
  close (errorFile)
}
getGoDepth = function(goTerm, ontology){
    if (ontology == 'BP')
        go.env = GOBPPARENTS
    else if (ontology == 'CC')
        go.env = GOCCPARENTS
    else if (ontology == 'MF')
        go.env = GOMFPARENTS

    term = goTerm
    depth = 0
    while (!is.null (term)) {
        term = go.env [[term]][1]
        depth = depth + 1
    }
    return (depth)
}
goTerm.info = function (goTerms){
  goTerms = intersect (goTerms, ls (GOTERM))
  if (length (goTerms) == 0)
    return (NA)
  return (lapply (goTerms, function (t) get (t, env=GOTERM)))
}

new.genes.per.GO.term = function (hyperGResult)
{
  if (verbose) cat ('--- new.genes.per.GO.term\n')
  genes = geneIds (hyperGResult)
  goTermToGeneID.list <<- geneIdUniverse (hyperGResult)
  goTerms = as.character (names (goTermToGeneID.list))

  result = list ()
  for (i in 1:length (goTerms)) {
    goTerm = goTerms [i]
    genes.for.this.goTerm = c ()
    for (g in 1:length (genes)) {
      gene = genes [g]
      if (gene %in% goTermToGeneID.list [[goTerm]])
        genes.for.this.goTerm = c (genes.for.this.goTerm, gene)
      } # for g
    #cat (paste ("--- goTerm:", goTerm, "\n"))
    #cat (paste ("    genes.for.this.goTerm:", genes.for.this.goTerm, "\n"))
    result [[goTerm]] = genes.for.this.goTerm
    }# for i

  invisible (result)
}

write.node.attribute.file = function (dataList, attributeName, filename)
{
  if (verbose) cat (paste ('--- write.node.attibute.file:', filename, '\n'))

  fullFilename = filename
  f = file (fullFilename, 'w')
  cat (paste (attributeName, '\n', sep=''), file=f)

  names = names (dataList)
  for (n in 1:length (names)) {
    name = names [n]
    values = dataList [[name]]
    if (length (values) == 1)
     s = paste (name, ' = ', values [1], '\n', sep='')
    else {
      valueString = values [1]
      for (v in 2:length (values))
        valueString = paste (valueString, values [v], sep='::')
      s = paste (name, ' = (', valueString, ')\n', sep='')
      }
    #print (s)
    cat (s, file=f)
    }

  close (f)
}

write.network.file = function (goGraph, directory=targetDirectory)
{
  if (verbose) cat ('--- write.network.file\n')
  if (!file.exists (directory))
    dir.create (directory, recursive=TRUE)

  if (length (grep ('/$', directory)) == 0)
    directory = paste (directory, '/', sep='')

  cat(paste('working dir:', getwd(), sep=' '))
  fullFilename = paste (directory, 'network.sif', sep='')
  networkFile = file (fullFilename, 'w')
  edges = edges (goGraph)
  sourceNodeNames = names (edges)
  for (n in 1:length (sourceNodeNames)) {
    sourceNode = sourceNodeNames [[n]]
    targetNode = edges [[sourceNode]]
    if (length (targetNode) == 0)
      next
    s = paste (sourceNode, ' edge ', targetNode, '\n', sep='')
    cat (s, file=networkFile)
    } # for n

  close (networkFile)

}

create.cytoscape.data.files = function ()
{
  cat ('--- create.cytoscape.data.files\n')

  genes.by.go.term <<- new.genes.per.GO.term (xyz)

  write.network.file (goDag (xyz))
  write.node.attribute.file (pvalues (xyz), 'pvalue (class=java.lang.Double)',
                            paste(targetDirectory, 'pvalues.noa', sep="/"))
  write.node.attribute.file (geneCounts (xyz), 'dataSetCount (class=java.lang.Integer)',
                              paste(targetDirectory, 'dataSetCounts.noa', sep="/"))
  write.node.attribute.file (universeCounts (xyz), 'organismCount  (class=java.lang.Integer)',
                             paste(targetDirectory, 'organismCount.noa', sep="/"))
  write.node.attribute.file (genes.by.go.term, 'genes (class=java.lang.String)',
                             paste(targetDirectory, 'genes.noa', sep="/"))


}

standard.webstart.files = function ()
{
  return  (c ('cy.jnlp-raw', 'jarkey', 'makefile',
              'project', 'props', 'vizmap.props'))

}

copy.standard.webstart.files = function (source.directory='webstartFiles')
{
  if (verbose) {
    cat ('--- copy.standard.webstart.files\n')
    cat (paste ('    current directory: ', getwd (), '\n'))
    }

  files = standard.webstart.files ()
  for (f in 1:length (files)) {
    full.path = paste (targetDirectory, "webstartFiles", files [f], sep='/')
    if (verbose) {
      cat (paste ('    copying \n\t', full.path, ' \n\tto\n\t', targetDirectory, '\n'))
      cat (paste ('    exists? ', file.exists (full.path), '\n'));
      } # verbose
    file.copy (full.path, targetDirectory)
    } # for f

} # copy.standard.webstart.files

create.localized.jnlp.file = function (title, codebase)
{
  if (verbose) cat ('--- create.localized.jnlp.file\n')

  text.lines = scan (paste(targetDirectory, 'cy.jnlp-raw', sep="/"), what=character(0), sep='\n')
  f = file (paste(targetDirectory, 'cy.jnlp', sep="/"), 'w')
  for (i in 1:length (text.lines)) {
    next.line = text.lines [i]

    if (length (grep ('\\$TITLE\\$', next.line)) > 0)
      next.line = sub ('\\$TITLE\\$', title, next.line)

    if (length (grep ('\\$CODEBASE\\$', next.line)) > 0)
      next.line = sub ('\\$CODEBASE\\$', codebase, next.line)

    next.line = paste (next.line, '\n', sep='')
    cat (next.line, file=f)
    }# for i

  close (f)

}

build.data.jar = function ()
{
  if (verbose) cat ('--- build.data.jar\n')
  system ('make', intern=T)

}


targetDirectory <- "."
verbose <- TRUE
genes <-readGenes("params.txt")
organism <- readOrganism("params.txt")
ontologyType <- readOntologyType("params.txt")
outFilename <- readOutFilename("params.txt")

if(organism == "Homo sapiens"){
	require(org.Hs.eg.db)
	annotationPackage = "org.Hs.eg.db"
}else if(organism == "Mus musculus"){
	require(org.Mm.eg.db)
	annotationPackage = "org.Mm.eg.db"
}else if (organism == "Rattus norvegicus"){
	require(org.Rn.eg.db)
	annotationPackage = "org.Rn.eg.db"
}else if(organism == "Saccharomyces cerevisiae"){
	require(org.Sc.sgd.db)
	annotationPackage = "org.Sc.sgd.db"
}

xyz <<- try(go.enrichment(genes, ontologyType,annotationPackage, .5))
if(inherits(xyz, "try-error")){
    write.error.file(paste("No GO terms mapped to the submitted genes in our database.  Make sure correct organism is specified or try using different gene identifiers (try Entrez Gene Ids).  Technical info: ", xyz[1]))
}else{
    abc <<-tabular.go.enrichment(xyz)
	write.table(abc, outFilename, append = TRUE, quote=FALSE, sep="\t",,,, row.names=FALSE, col.names=TRUE,)
    myNodes <- nodes(goDag(xyz))
    infos <-goTerm.info(myNodes)
    for(i in 1:length(infos)){myNodes[i] = Term(infos[[i]])}
    write.node.attribute.file (myNodes, 'commonName',paste(targetDirectory, 'commonNames.noa', sep="/"))
    create.cytoscape.data.files()
    copy.standard.webstart.files  ()
    directoryTokens = unlist (strsplit (getwd(), '/'))
    codeBaseDir = directoryTokens [length (directoryTokens)]
    codebase = paste ('http://pipe2.systemsbiology.net/PIPE2/PIPEletResourceDir/GOTableEnrichment', codeBaseDir, sep='/')
    cat(codebase)
    webstartTitle = paste(organism, ontologyType, sep=' - ')
    create.localized.jnlp.file  (webstartTitle, codebase)
    build.data.jar ()
    completeFile = file ('COMPLETE', 'w')
    cat ("complete", file=completeFile)
    close (completeFile)
}
