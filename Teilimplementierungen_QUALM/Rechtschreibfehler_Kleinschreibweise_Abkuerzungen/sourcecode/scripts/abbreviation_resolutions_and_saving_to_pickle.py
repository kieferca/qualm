'''
Script for resolve found abbreviations in a CONLL like annotated corpus and
saves the new corpus as a pickle.

When pointing to a directory, all files will be patched.
'''

import os

input_annotated_file = r'''C:\Users\xx\Dx'''
input_abbreviation_list = r'''C:\Users\xx.txt'''
output_pickle = r'''C:\Users\xx'''

def SavePickle( data, fname ):
    from pickle import dump
    f = open('{:s}.pickle'.format(fname), 'wb')
    dump(data, f, -1)
    f.close()

# load the list of abbreviations    
abbr_resolutions = {}    
with open(input_abbreviation_list, 'r', encoding='utf8') as file:
    for line in file:

        line = line.replace('\n', '')
        line = line.split('\t')
        if len(line) > 1:
            resolution = line[1]
            if resolution is not None:
                if resolution != '':
                    abbr_resolutions[line[0]] = resolution
                                    
corpora = []
sentences = []
count = 0

# patch the file
if os.path.isfile(input_annotated_file):  
    with open(input_annotated_file, 'r', encoding='utf8') as file:
        sent = []
        for line in file:
            line = line.replace('\n', '')
            line = line.split('\t')
            if len(line) > 1:
                words = []
                if line[2] == 'ABBR':
                    if line[0] in abbr_resolutions:
                        count += 1
                        abbr_res = abbr_resolutions[line[0]]
                        if len(sent) == 0:
                            abbr_res = abbr_res[0].upper() + abbr_res[1:]
                        abbr_res = abbr_res.split(' ')
                        for word in abbr_res:
                            sent.append(word)
                        continue
                sent.append(line[0])
            else:
                sentences.append(sent)
                sent = []
                
    SavePickle(sentences, output_pickle)
    print(count)

# patch files in directory
elif os.path.isdir(input_annotated_file):  
    if not os.path.isdir(output_pickle):
        print('output pickle must be an existing directory if input is a directory.')
        exit(0)
    for filename in os.listdir(input_annotated_file):
        # specify the endings of the files to patch
        if not filename.endswith('.conllu'):
            output = os.path.join(output_pickle, filename + '_patched_abbreviation')

            print(filename)
            with open(os.path.join(input_annotated_file, filename), 'r', encoding='utf-8') as file:
                sent = []
                for line in file:
                    line = line.replace('\n', '')
                    line = line.split('\t')
                    if len(line) > 1:
                        words = []
                        if line[2] == 'ABBR':
                            if line[0] in abbr_resolutions:
                                count += 1
                                abbr_res = abbr_resolutions[line[0]]
                                if len(sent) == 0:
                                    abbr_res = abbr_res[0].upper() + abbr_res[1:]
                                abbr_res = abbr_res.split(' ')
                                for word in abbr_res:
                                    sent.append(word)
                                continue
                        sent.append(line[0])
                    else:
                        sentences.append(sent)
                        sent = []
                        
            SavePickle(sentences, output)
    print(count)