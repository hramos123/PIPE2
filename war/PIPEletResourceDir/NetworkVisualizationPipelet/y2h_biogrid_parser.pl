my $counter = 0;
%types;
@acceptableTypes = ("Two-hybrid", "Affinity Capture-Western", "Affinity Capture-MS");
$j = 0;while(<>){
	chomp;
	@a = split(/\t/);

	for($i=0; $i <= $#acceptableTypes; $i++){
		if($acceptableTypes[$i] eq $a[6]){
			$types{$a[6]}++;
			$counter++;
			print "$a[0]\t$a[2]\t$a[1]\t$a[3]\t$a[6]\n";
		}
	}
}

#print "counter: $counter\n";
#foreach $key (keys %types){
#	print "$key\t-->\t$types{$key}\n";
#}