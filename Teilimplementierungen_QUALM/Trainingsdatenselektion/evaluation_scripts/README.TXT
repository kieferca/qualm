This directory contains evaluation scripts for sample Taggers in NLTK. More taggers may be added as listed here: 
https://www.nltk.org/api/nltk.tag.html

NLTK is required and needs to be installed: http://www.nltk.org/
For the CRF Tagger, CRF suite needs to be installed: https://pypi.python.org/pypi/python-crfsuite

The scripts starting with tagger_eval perform evaluations for the respective taggers.
The script for the perceptron tagger is split into separate parts to ensure a valid evaluation. 

The script export_corpora exports six sample corpora used in this thesis as a text representation. The other corpora 
(parts of the Brown corpus collection) can be added by adding the names listed here: https://www.nltk.org/book/ch02.html in Table 1.1

Note that for the export to work for twitter, the twitter data in conll format needs to be put
in the folder resources. 

The twitter data set can be obtained from: https://github.com/brendano/ark-tweet-nlp/




