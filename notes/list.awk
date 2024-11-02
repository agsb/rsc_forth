BEGIN {
    FS=" "

    ofs = strtonum("0x" "F000")
}

{
    mem = strtonum("0x" $1) + ofs

    for (i = 2; i <= NF; i++) {

        printf (" %06x %2s\n", mem, $i)
    
        mem++
        
    }
}        

END {
}
