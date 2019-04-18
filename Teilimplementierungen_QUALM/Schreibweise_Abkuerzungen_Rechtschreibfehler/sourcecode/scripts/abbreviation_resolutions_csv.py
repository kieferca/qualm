'''
Script for resolve found abbreviations in a CSV file where a token has also
its abbreviation tag at the end. For Example:
    Q/ABBR
    Quality/O
a new CSV file will be created with the resoluted tokens (if they are in the
resolutions list).
'''

import os
import csv
import re

encoding = 'mbcs'
delimiter = '\t'
has_header = True
text_columns = [8, 9]

input_annotated_csv_file = r'''C:\Users\xx.csv'''
input_abbreviation_list = r'''Z:\Abkuerzungen\corpora\abbreviation_lists\xx.txt'''

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

count = 0                                    
new_dataset = []
pattern = re.compile(r"""\s?(?P<word>.*?)/(?P<tag>O|ABBR)""", re.VERBOSE)
# FOR ScHleife LOAD CSV EVERY text per ROW
with open(input_annotated_csv_file, encoding=encoding, newline='') as csv_file:
    j = 0
    csv_reader = csv.reader(csv_file, delimiter=delimiter)
    for row in csv_reader:
        new_row = row
        # if a header is specified - just ignore it
        if has_header:
            if j == 0:
                j += 1
                new_dataset.append(new_row)
                continue

        for text_column in text_columns:
            annotated_text = row[text_column]
            annotated_text = re.findall(pattern, annotated_text)
            sent = ""
            for word, tag in annotated_text:
                if tag == 'O':
                    sent = " ".join([sent, word])
                elif tag == 'ABBR':
                    if word in abbr_resolutions:
                        word = abbr_resolutions[word]
                        count += 1
                    sent = " ".join([sent, word])
            new_row[text_column] = sent
        new_dataset.append(new_row)
        j += 1

print('Anzahl aufgelöster Abkürzungen: ' + str(count))

file_name, file_extension = os.path.splitext(input_annotated_csv_file)
with open("{:s}_abbr_res{:s}".format(file_name, file_extension), 'w', encoding=encoding, newline='') as csv_file:
    writer = csv.writer(csv_file, delimiter=delimiter)
    writer.writerows(new_dataset)

