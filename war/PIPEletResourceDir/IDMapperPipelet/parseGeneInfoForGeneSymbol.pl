#######################
# Create gene_symbol -> gene_id for human, mouse, and rat (split into dif. files)
# .  data read from gene_info from entrez
###########################
my %taxonomies = {"9096" => "human", "10090" => "mouse", "10116" => "rat", "4932" => "yeast"};
open HUMAN_OUT,">geneSymbol-geneID_human.tsv"or die("nope : " . ">geneSymbol-geneID_human.tsv");
open MOUSE_OUT,">geneSymbol-geneID_mouse.tsv" or die("nope : >geneSymbol-geneID_mouse.tsv");
open RAT_OUT,">geneSymbol-geneID_rat.tsv" or die("nope: " . ">geneSymbol-geneID_rat.tsv");
open YEAST_OUT,">geneSymbol-geneID_yeast.tsv" or die("nope: " . ">geneSymbol-geneID_yeast.tsv");
%output_files = ("9606"=> HUMAN_OUT, "10090"=> MOUSE_OUT,
                "10116"=> RAT_OUT, "4932" => YEAST_OUT);
my $i = 0;
print "Symbol\tGene_ID\n";
while(<>){
	chomp;
	#if yeast, human, mouse, or rat
	if(/^(10090|9606|10116|4932)/){
		$org_id = $1;
#		if($i++ > 500000){last;};
		@a = split(/\t/);
		$gene_id = $a[1];
		$gene_symbol = $a[2];
		$locus = $a[3];
		$synonyms = $a[4];
		$description = $a[8];
		print  {$output_files{$org_id}} "$gene_symbol\t$gene_id\n";
		foreach $syn (split(
	}
}
#print "\ntotal: $i\n";

close HUMAN_OUT;
close MOUSE_OUT;
close RAT_OUT;
close YEAST_OUT;