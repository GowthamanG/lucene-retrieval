# lucene-retrieval

This implementation was a part of an exercise submission of the lecture ["Multimedia Retrieval"](https://dmi.unibas.ch/de/studium/computer-science-informatik/lehrangebot-hs20/lecture-multimedia-retrieval/) at the University of 
Basel.

Lucene is a fuzzy retrieval library for searching documents. It can be embedded in applications, that require text
retrieval function. Further information can be found on Lucene's website: <https://lucene.apache.org/>.

The code is split into an *offline* and *online* module:
- *offline*: Add and analyze documents (feature extraction)
- *online*: Search for documents (comparison on feature level)

## How to Run:

1. Build gradle project
```console
   gradle build
```

2. Run project
```console
   gradle run
```

3. Type any song title you want to search for, it returns a ranked list containing 5 elements (score, ID, artist, song, year)
