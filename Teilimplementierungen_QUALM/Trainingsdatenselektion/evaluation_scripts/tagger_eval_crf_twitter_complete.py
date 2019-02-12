import nltk
from nltk.tag import CRFTagger

import readData as d

#treebank
c_tree = CRFTagger()
c_tree.train(d.tree,'model.crf.tagger')
print("Tree: ")
print(c_tree.evaluate(d.tree))
print(c_tree.evaluate(d.brown_fiction))
print(c_tree.evaluate(d.brown_reviews))
print(c_tree.evaluate(d.conll))
print(c_tree.evaluate(d.nps))
print(c_tree.evaluate(d.twitter))

#brown_fiction
print("Brown Fiction: ")
c_brown_fiction = CRFTagger()
c_brown_fiction.train(d.brown_fiction,'model.crf.tagger')
print(c_brown_fiction.evaluate(d.tree))
print(c_brown_fiction.evaluate(d.brown_fiction))
print(c_brown_fiction.evaluate(d.brown_reviews))
print(c_brown_fiction.evaluate(d.conll))
print(c_brown_fiction.evaluate(d.nps))
print(c_brown_fiction.evaluate(d.twitter))

#brown_reviews
print("Brown Reviews: ")
c_brown_reviews = CRFTagger()
c_brown_reviews.train(d.brown_reviews,'model.crf.tagger')
print(c_brown_reviews.evaluate(d.tree))
print(c_brown_reviews.evaluate(d.brown_fiction))
print(c_brown_reviews.evaluate(d.brown_reviews))
print(c_brown_reviews.evaluate(d.conll))
print(c_brown_reviews.evaluate(d.nps))
print(c_brown_reviews.evaluate(d.twitter))

#CoNNL
print("CoNNL Reviews: ")
c_conll = CRFTagger()
c_conll.train(d.conll,'model.crf.tagger')
print(c_conll.evaluate(d.tree))
print(c_conll.evaluate(d.brown_fiction))
print(c_conll.evaluate(d.brown_reviews))
print(c_conll.evaluate(d.conll))
print(c_conll.evaluate(d.nps))
print(c_conll.evaluate(d.twitter))

#NPS
print("NPS: ")
c_nps = CRFTagger()
c_nps.train(d.nps,'model.crf.tagger')
print(c_nps.evaluate(d.tree))
print(c_nps.evaluate(d.brown_fiction))
print(c_nps.evaluate(d.brown_reviews))
print(c_nps.evaluate(d.conll))
print(c_nps.evaluate(d.nps))
print(c_nps.evaluate(d.twitter))

#Twitter
print("Twitter: ")
c_twitter = CRFTagger()
c_twitter.train(d.twitter,'model.crf.tagger')
print(c_twitter.evaluate(d.tree))
print(c_twitter.evaluate(d.brown_fiction))
print(c_twitter.evaluate(d.brown_reviews))
print(c_twitter.evaluate(d.conll))
print(c_twitter.evaluate(d.nps))
print(c_twitter.evaluate(d.twitter))

