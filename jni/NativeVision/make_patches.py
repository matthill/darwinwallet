#!/bin/python

import yaml
import os
import PythonMagick as Magick	#PythonMagick


PIXELS_PER_WIDTH_INCH = 54.234527687

dirList=os.listdir('./currency/')
for fname in dirList:
	if fname.endswith('.yaml'):
		
		f = open('./currency/' + fname)

		locality = fname[:2]

		OUTDIR_BASE = '/home/mhill/projects/darwin_wallet/DarwinWallet/res/raw/'

		patch_config = yaml.load(f)
		f.close()
		from commands import *

		#print patch_config

		for patch in patch_config['bills']:
			#input_file = './currency/' locality + '/' + bill + '.png'

			bill = patch['type']
			print 'processing: ' + bill 

			if 'input' not in patch:
				in_file = bill + '.png'
			else:
				in_file = patch['input']

			input_file = './currency/' + locality + '/' + in_file
			image = Magick.Image(input_file)

			if 'width' not in patch:
				# Don't crop
				pass
			else:
				crop_string = str(patch['width']) + "x" + str(patch['height']) + "+" + str(patch['left']) + "+" + str(patch['top'])
				image.crop( crop_string )

	

			print "  - patch: " + patch['patch']
			#print image.fileName()
			#print image.magick()
			#print image.size().width()
			#print image.size().height()

			# Width x Height + left + top
			desired_width = patch['inch_width'] * PIXELS_PER_WIDTH_INCH
			scale_factor = round( (desired_width / image.size().width()) * 100, 2 )
			#print 'computed width;' + str( image.size().width() )
			image.scale(str(scale_factor) + '%')

			image.borderColor("#ffffff")
			#image.border("34x34")

			out_dir = OUTDIR_BASE
			if not os.path.exists(out_dir):
			    os.makedirs(out_dir)
	
			out_file = out_dir + '/' + locality + bill + patch['patch'] + '.jpg'
			image.write( out_file )

			# Process with montage
			#getstatusoutput('montage -adjoin -geometry +0+0 ' + out_dir + '/*.png ./currency/outs/' + bill + '.png')
