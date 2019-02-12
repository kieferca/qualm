import nltk 
from nltk.stem import WordNetLemmatizer
import codecs
import itertools

import codecs
codecs.register(lambda name: codecs.lookup('utf-8') if name == 'cp65001' else None)

import re


#####npschat
def export (corpus, prefix):
    corpus = list(itertools.chain.from_iterable(corpus)) #flatten list
    corpus = [x[0] for x in corpus] #strip tags if there are any

    text = " ".join(corpus)
    #remove the "|" character due to issues with LSA
    text = re.sub('\|', '', text) 
    filename = prefix + '.txt'
    outfile = codecs.open(filename, "w", "utf-8-sig")
    outfile.write(text) 
    outfile.close()

    #remove the "|" character due to issues with LSA
    text = [re.sub('\|', '', w) for w in corpus]
    
    filename = prefix + '_lemma.txt'    
    wnl = WordNetLemmatizer()
    out = " ".join([wnl.lemmatize(i) for i in text])
    out = " ".join([wnl.lemmatize(i, 'v') for i in text])
    outfile = codecs.open(filename, "w", "utf-8-sig")
    outfile.write(out) 
    outfile.close()
    return



