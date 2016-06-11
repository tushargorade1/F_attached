//import required libraries
var bedJS = require('bedjs');
var fs = require('fs');
//Read the bed file 
var bed = bedJS.Read('truseq.bed');
//Read the vcf file 
var data = fs.readFileSync('mutect_immediate.vcf');
//created an object for excel file
var writeStream = fs.createWriteStream("accepted.xls");
//header for excel file
var header="#CHROM"+"\t"+"POS"+"\t"+"ID"+"\t"+"REF"+"\t"+"ALT"+"\t"+"QUAL"+"\t"+"FILTER"+"\t"+"INFO"+"\t"+"FORMAT"+"\t"+"none"+"\t"+"LungCancer"+'\n';
writeStream.write(header);
data=data.toString();
var vcf=[];
//spliting .vcf file for each line
vcf=data.split('\n');
for(var i=0;i<vcf.length;i++){
	var templine=vcf[i];
	var flag=0; 
    if(templine.indexOf("##") > -1){
	 console.log(templine);
	}
	else{
		flag=checkMutation(templine);
		if(flag)
		{
			console.log(templine);
			writeStream.write(templine+'\n');
		}
	}
}
//checking the mutaion accepted in region
function checkMutation(templine){
	var mutation=[];
	mutation=templine.split('\t');
	
	if(mutation[1]!=undefined){
		for(var i=0;i<bed.length;i++){
			if(bed[i].start<mutation[1]&&mutation[1]<bed[i].end){
				console.log(bed[i]);
				return 1;
			}
		}
	}
	return 0;
}
console.log("VCF",vcf.length);