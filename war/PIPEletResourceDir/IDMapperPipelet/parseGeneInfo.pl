#######################
# Take the relevant information out of gene_info (from entrez gene)
#tax_id GeneID Symbol LocusTag Synonyms dbXrefs chromosome map_location description type_of_gene Symbol_from_nomenclature_authority Full_name_from_nomenclature_authority Nomenclature_status Other_designations Modification_date (tab is used as a separator, pound sign - start of a comment)
###########################

my $i = 0;
print "GeneID\tSymbol\tDescription\n";
while(<>){
	#if($i == 0){print;$i++;}
	chomp;
	#if yeast, human, mouse, or rat
	if(/^9606/ or /^10090/ or /^10116/ or /^4932/){
		#if($i++ > 1000){last;};
		@a = split(/\t/);
		$gene_id = $a[1];
		$gene_symbol = $a[2];
		$locus = $a[3];
		$synonyms = $a[4];
		$description = $a[8];
		#print "$locus\t$gene_id\t$gene_symbol\t$description\n";
		print "$gene_id\t$gene_symbol\t$description\n";
	}
}
#print "\ntotal: $i\n";