BEGIN {
    FS = ","
}

{
    print "Processing record number " NR

    # Use printf to format output
    printf "First field: %s, Second field: %s\n", $1, $2

    # Use getline to read the next line into variable 'line'
    getline
    print "Next line is: " $0

    # Use next to skip processing the rest of the current record
    if (NR == 2) {
        print "Skipping processing of second record"
        next
    }

    # Use gsub to replace all occurrences of 'a' with 'b' in first field
    gsub("a", "b", $1)
    print "First field after gsub: ",$1

    # Use index to find position of 'b' in first field
    idx = index($1, "i")
    print "Position of 'i' in first field: " idx

    # Use length to get length of first field
    len = length($1)
    print "Length of first field: " len

    # Use match to find position of regex match in first field
    j=match($1, "b")
    print "Position of regex match in first field: " j 

    # Use split to split first field into array 'a' on 'b'
    n = split($1, a, "b")
    print "First field split into " n " parts"

    # Use sprintf to format a string and assign to variable 's'
    s = sprintf("First part of split: %s" , a[0])
    print s

    # Use sub to replace first occurrence of 'b' with 'a' in first field
    sub("b", "a",  $1)
    print "First field after sub: ", $1

    # Use substr to get a substring of first field
    sub_str = substr($1, 1, 3)
    print "Substring of first field: " ,sub_str

    # Use tolower to convert first field to lower case
    lower = tolower($1)
    print "First field in lower case: ", lower

    # Use toupper to convert first field to upper case
    upper = toupper($1)
    print "First field in upper case: ", upper
}
