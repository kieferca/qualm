import nltk
import readData as d


    

#Twitter
print("CoNLL: ")
tagger = nltk.tag.PerceptronTagger(load='false')
tagger.train(d.conll)
print(tagger.evaluate(d.tree))
print(tagger.evaluate(d.brown_fiction))
print(tagger.evaluate(d.brown_reviews))
print(tagger.evaluate(d.conll))
print(tagger.evaluate(d.nps))
print(tagger.evaluate(d.twitter))

