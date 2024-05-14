BEGIN { word_count = 0 }
{
    word_count += NF
}
END { print word_count, "words" }
