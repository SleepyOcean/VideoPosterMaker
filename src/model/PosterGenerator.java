package model;

import java.awt.Color;
import java.awt.Font;

import config.Configuration;
import filter.IFilter;
import filter.LomoFilter;
import tools.StringOperator;
import view.Param;

/**
 * @name: PosterGenerator.java
 * @author: sleepyocean
 * @date: created in 2018年3月31日 下午1:16:41
 * @version: 1.0
 * @description: TODO (write your description)
 */

public class PosterGenerator {

	static IFilter filter;
	static PosterCharacter character;

	public static String automaticGenerate(PosterProduction production) {
		String defaultPath;

		setAutomaticFilter();
		setAutomaticCharacter();

		defaultPath = character.apply(
				filter.apply(getDefaultKeyFrame(), StringOperator.getUpperDir(getDefaultKeyFrame()) + "default.jpeg"));
		if (production.getCharacter() == null) {
			production.setCharacter(character);
		}
		return defaultPath;
	}

	public static String applyNewChange(PosterProduction production) {
		return production.getCharacter().apply(production.getFilter().apply(production.getSourcePath(),
				StringOperator.getUpperDir(getDefaultKeyFrame()) + production.getFilter().getFilterName() + ".jpeg"));
	}

	private static void setAutomaticFilter() {
		// TODO: 滤镜选择算法
		filter = new LomoFilter();
	}

	private static void setAutomaticCharacter() {
		// TODO: 海报描述生成算法
		character = new PosterCharacter("Poster", "Made by Video Poster Maker", Param.CharacterPos.CENTER,
				new Font("Serif", Font.BOLD, 100), Color.BLACK, new Font("Serif", Font.BOLD, 20), Color.WHITE);
	}

	private static String getDefaultKeyFrame() {
		return Configuration.getKeyFramePath() + Configuration.getKeyFramePrefixName() + "1.jpeg";
	}
}
