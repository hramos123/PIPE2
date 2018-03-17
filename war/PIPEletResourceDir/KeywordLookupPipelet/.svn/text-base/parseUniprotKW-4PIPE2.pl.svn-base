###########################
# This script takes the swissprot/uniprot text-database as input and for
# Human, Mouse, Rat,and Yeast prints GeneID\tswissprotKeyword in seperate files -one for each org.
# If GeneID wasn't found, then it prints the UniProt ID.
#
# call: perl parseUniprotKW-4PIPE2.pl < uniprot_sprot.dat
# output: sp_kw_9606.tsv, sp_kw_10090.tsv, sp_kw_10116.tsv, sp_kw_4932.tsv
##########################################
$out = "";
@geneIDs = ();
$keyword= "";
$humanMouseRatOrYeast = "FALSE";
$transmembrane = "FALSE";
$tissueSpecificity = "";
$induction ="";
$spID = "";
$subCelLoc = "";
$hecGICounter = 0;
$hecspCounter = 0;
my @organisms = ("Homo sapiens", "Mus musculus", "Rattus norvegicus", "Saccharomyces cerevisiae");
my %tax_ids = ($organisms[0]=> "9606",$organisms[1]=> "10090",
                $organisms [2]=> "10116", $organisms[3]=>"4932");

open HUMAN_OUT,">sp_kw_" . $tax_ids{$organisms[0]} . ".tsv" or die("nope : " . ">sp_kw_" . $tax_ids{$organisms[0]} . ".tsv");
open MOUSE_OUT,">sp_kw_" . $tax_ids{$organisms[1]} . ".tsv" or die("nope : >sp_kw_" . $tax_ids{$organisms[1]} . ".tsv");
open RAT_OUT,">sp_kw_" . $tax_ids{$organisms[2]} . ".tsv" or die("nope: " . ">sp_kw_" . $tax_ids{$organisms[2]} . ".tsv");
open YEAST_OUT,">sp_kw_" . $tax_ids{$organisms[3]} . ".tsv" or die("nope : " . ">sp_kw_" . $tax_ids{$organisms[3]} . ".tsv");
%output_files = ($organisms[0]=> HUMAN_OUT, $organisms[1]=> MOUSE_OUT,
                $organisms [2]=> RAT_OUT, $organisms[3]=> YEAST_OUT);
$org = "";
my %results = ($organisms[0]=> (),$organisms[1]=> (),
                $organisms [2]=> (), $organisms[3]=>());
my $i = 0;
while(<>){
#        if($i++ > 1000000){last;}
        chomp;
        if(/^\/\//){
                if($humanMouseRatOrYeast eq "TRUE" and ($keyword ne "")){
                foreach $kw (split(/; /,$keyword)){
                    #remove trailing period (.)
                    $kw =~ s/\.$//;
                    if($#geneIDs > -1){
#                        print {$output_files{$org}} "$kw\t" ;#+ join('; ', @geneIDs) + "\t$spID\n";
#                        my $lkjasdf = 0;
                        foreach $key (@geneIDs){
                            ${${${$results{$org}}{$kw}}{"gene_ids"}}{$key}++;
#                            if($lkjasdf++ > 0){
#                                print {$output_files{$org}} "; ";
#                            }
#                            print {$output_files{$org}} "$key";#\t$keyword\t$subCelLoc\t$tissueSpecificity\t$induction\n";
                            $hecGICounter++;
                        }
#                        print {$output_files{$org}} "\t$spID\n";
                    }else{
                        ${${${$results{$org}}{$kw}}{"sp_ids"}}{$spID}++;
#                        print {$output_files{$org}} "$kw\t$spID\t$spID\n";
                        $hecspCounter++;
                    }
                }
            }
		    #reset everything
            $humanMouseRatOrYeast = "FALSE";
            @geneIDs = ();
            $keyword = "";
            $spID = "";
            $org = "";
        }else{
            if(/^ID   (\w+)/){
                $spID = $1;
		    }
		
	        if(/^OS   Homo sapiens/ or /^OS   Mus musculus/ or /^OS   Rattus norvegicus/ or /^OS   Saccharomyces cerevisiae/){
                	$org = $_;
                	$org =~ s/^OS   //;
                	$org =~ s/^(\w+ \w+).*$/$1/;
                	$humanMouseRatOrYeast = "TRUE";
        	}

		if(/^DR   GeneID;\W+(\d+);.*/){
            $geneIDs[$#geneIDs + 1] = $1;
        }
        if(/^KW   /){
            $keyword = $_;
            $keyword =~ s/^KW   //;
            $line2 = <>;
			chomp $line2;
            while($line2 =~ /^KW   /){
                $line2 =~ s/^KW   //;
                #$out .= $line2 . "\n";
                $keyword .= " $line2";
                $line2 = <>;
                chomp $line2;
            }
            $_ = $line2;
           }
        }
}
print "hecGICounter:\t $hecGICounter\n";
print "hecspCounter:\t $hecspCounter\n";

#print results
foreach $org (keys %results){
    foreach $kw (sort keys %{$results{$org}}){
        print {$output_files{$org}} "$kw\t" . join('; ',sort { $a <=> $b } keys %{${${$results{$org}}{$kw}}{"gene_ids"}}) . "\t" . join('; ',sort keys %{${${$results{$org}}{$kw}}{"sp_ids"}}) . "\n";
    }
}

close HUMAN_OUT;
close MOUSE_OUT;
close RAT_OUT;
close YEAST_OUT;