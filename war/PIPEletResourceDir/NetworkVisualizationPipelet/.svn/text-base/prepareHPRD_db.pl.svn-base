my %geneSymbol_geneID = {};
my %geneID_geneSymbol = {};

#create forward and backward look up tables
open geneIDmapperFile, "<HPRD_ID_MAPPINGS.txt" or die("could not open file");
while(<geneIDmapperFile>){
    chomp;
    @a = split(/\t/);
    push((@{$geneSymbol_geneID{$a[1]}}), $a[4]);
}
close geneIDmapperFile;
$i = 0;

#translate BINARY_PROTEIN_PROTEIN_INTERACTIONS.txt
open INTERACTIONS, "<BINARY_PROTEIN_PROTEIN_INTERACTIONS.txt" or die "could not open interactions file";
open FILEOUT, ">HPRD_8_P_P_interactions_pipe2.txt" or die "could not open outfile";
while(<INTERACTIONS>){
    chomp;
    @a = split(/\t/);
    if($a[0] ne "-" && $a[3] ne "-"){
        print FILEOUT "$a[0]\t$a[3]\t$a[6]\n";
        @geneIDs_A = @{$geneSymbol_geneID {$a[0]}};
        @geneIDs_B = @{$geneSymbol_geneID {$a[3]}};

        foreach $geneIDa (@geneIDs_A){
            foreach $geneIDb (@geneIDs_B){
                print  FILEOUT "$geneIDa\t$geneIDb\t$a[6]\n";
            }
        }
    }
#    @geneSymbols_A = @{$geneSymbol_geneID {$a[0]}};
#    @geneSymbols_B =@ {$geneSymbol_geneID {$a[3]}};
#    foreach $geneIDa (@geneIDs_A){
#        foreach $geneIDb (@geneIDs_B){
#            print "\t$geneIDa\t$geneIDb\t$a[6]\n";
#        }
#    }
#    if($i++ > 1000){
#        last;
#    }
}
close FILEOUT;
close INTERACTIONS;

#foreach $key (keys %geneID_geneSymbol ){
#    if($i++ > 50){
#        last;
#    }
#    foreach $val (@{$geneID_geneSymbol {$key}}){
#        print "$key\t$val\n";
#    }
#}