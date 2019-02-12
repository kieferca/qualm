import nltk
from nltk.corpus import brown
from nltk.corpus import brown
from nltk.corpus import treebank
from nltk.corpus import nps_chat
from nltk.corpus import conll2000
from nltk.corpus import ConllCorpusReader

import corpusexport

import readData as d


corpusexport.export(d.tree, 'treebank')
corpusexport.export(d.brown_reviews, 'brown_reviews')
corpusexport.export(d.brown_fiction, 'brown_fiction')
corpusexport.export(d.twitter, 'twitter')
corpusexport.export(d.conll, 'conll')
corpusexport.export(d.nps, 'nps')

