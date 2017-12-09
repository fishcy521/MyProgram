/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: GMailEngine.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package com.rms.commons.filter;

import java.awt.Color;
import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * JCaptcha楠岃瘉鐮佸浘鐗囩敓鎴愬紩鎿�浠跨収JCaptcha2.0缂栧啓绫讳技GMail楠岃瘉鐮佺殑鏍峰紡.
 * 
 */
public class GMailEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;
		int maxWordLength = 5;
		// int fontSize = 60;
		int imageWidth = 110;
		int imageHeight = 30;

		// // word generator
		// WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		// // word2image components
		// TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength, new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),
		// new Color(23, 67, 172) }), new TextDecorator[] {});
		// BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
		// FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] { new Font("nyala", Font.BOLD, fontSize), new Font("Bell MT", Font.PLAIN, fontSize),
		// new Font("Credit valley", Font.BOLD, fontSize) });
		//
		// ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		// ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		// ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});
		//
		// WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, postDef);
		// addFactory(new GimpyFactory(dictionnaryWords, word2image));

		// 闅忔満鐢熸垚鐨勫瓧绗�abcdefghijklmnopqrstuvwxyz123456789
		WordGenerator wgen = new RandomWordGenerator("0123456789");
		//RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(new int[] { 0, 100 }, new int[] { 0, 100 }, new int[] { 0, 100 });
		// 鏂囧瓧鏄剧ず鐨勪釜鏁�
		// TextPaster textPaster = new RandomTextPaster(minWordLength, maxWordLength, cgen, true);
		TextPaster textPaster = new DecoratedRandomTextPaster(minWordLength, maxWordLength, new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11),
				new Color(23, 67, 172) }), new TextDecorator[] {});
		// 鍥剧墖鐨勫ぇ灏�
		// BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(imageWidth, imageHeight);
		BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.white);
		// 瀛椾綋鏍煎紡
		Font[] fontsList = new Font[] { new Font("Arial", Font.BOLD, 10), new Font("Tahoma", 0, 10), new Font("Verdana", 0, 10), };
		// 鏂囧瓧鐨勫ぇ灏�
		FontGenerator fontGenerator = new RandomFontGenerator(new Integer(18), new Integer(20), fontsList);
		WordToImage wordToImage = new ComposedWordToImage(fontGenerator, backgroundGenerator, textPaster);
		this.addFactory(new GimpyFactory(wgen, wordToImage));
	}
}
