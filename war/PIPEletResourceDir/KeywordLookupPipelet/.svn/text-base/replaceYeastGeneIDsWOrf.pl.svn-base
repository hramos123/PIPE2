open mappingfile, "<../IDMapperPipelet/geneInfoYeast11-02-09.txt" or die "couldn't open yeast mapping file";

my %mappings;
while($line = <mappingfile>){
	chomp;
	@a = split(/\t/,$line);
	$yeastORF = $a[0];
	$geneID = $a[1];
	
	if($geneID ne "" and $yeastORF ne ""){
		$mappings{$geneID} = $yeastORF;
	}
}
close mappingfile;

open YEASTUNIPROTDATA, "<sp_kw_4932.tsv" or die("couldn't open sp_kw_4932.tsv");
while($line = <YEASTUNIPROTDATA>){
	chomp;
	@a = split(/\t/,$line);
	$uniprot_kw = $a[0];
	$geneIDs = $a[1];
	@b = split(/; /, $geneIDs);
	@yeastORFs = ();
	for($i = 0; $i <= $#b; $i++){
		if(exists($mappings{$b[$i]})){
			#print "$b[$i]\t->\t$mappings{$b[$i]}\n";
			push (@yeastORFs, $mappings{$b[$i]});
		}else{
			push (@yeastORFs, $b[$i]);
		}
	}
	print "$uniprot_kw\t" . join('; ', @yeastORFs) . "\n";	
}

close YEASTUNIPROTDATA;