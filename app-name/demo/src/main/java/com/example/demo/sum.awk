#This is begining of the code
BEGIN {FS=","; total_sum =0}
# The following block holds the code which will give output(if asked in there) for every single line of input.
{     n=0
      sum=0
      
        for (i=1;i<=NF;i++)
        {
             sum=sum +  $i    
             total_sum = total_sum + $i
        }
             n= NR
print "Line " n " is " sum
}
END{print "The total is " total_sum}
