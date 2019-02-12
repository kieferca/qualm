import nltk
from nltk.corpus import treebank
from nltk.corpus import brown
from nltk.corpus import nps_chat
from nltk.corpus import conll2000
from nltk.corpus import ConllCorpusReader


brown_fiction = list(brown.tagged_sents(categories='fiction', tagset='universal'))
brown_reviews = list(brown.tagged_sents(categories='reviews', tagset='universal'))
conll = list(conll2000.tagged_sents(tagset='universal'))
tree = list(treebank.tagged_sents(tagset='universal'))

columntypes = ['words', 'pos']
twitter_corpus = ConllCorpusReader("resources/", "twitter.conll", columntypes, tagset='en-tweet')
twitter = list(twitter_corpus.tagged_sents(tagset='universal'))

nps_raw = nps_chat.tagged_posts(tagset='universal')
nps = []
for post in nps_raw:
    post_clean = [sub for sub in post if sub[0]]
    nps.append(post_clean)
