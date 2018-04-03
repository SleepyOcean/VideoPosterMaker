package model;

import config.Configuration;
import filter.IFilter;
import filter.LomoFilter;
import tools.StringOperator;
import tools.VideoKeyFrameGetter;
import view.ViewSize;

/**
 * @name: PosterProduction.java
 * @author: sleepyocean
 * @date: created in 2018年3月25日 下午4:09:44
 * @version: 1.0
 * @description: 视频海报作品info类
 */

public class PosterProduction {
	private String videoPath;
	/** 海报原图路径 */
	private String sourcePath;
	/** 海报保存路径*/
	private String destPath;
	
	private PosterCharacter character;
	private IFilter filter;
	private ViewSize posterSize;

	public PosterProduction(String videoPath, String destPath) {
		this.videoPath = videoPath;
		this.sourcePath = Configuration.getKeyFramePath()+ Configuration.getKeyFramePrefixName() + "1.jpeg";
		this.destPath = destPath;
		int[] size = getPosterSize();
		posterSize = new ViewSize(size[0], size[1]);
		filter = new LomoFilter();
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public int getWidth() {
		return posterSize.getWidth();
	}

	public int getHeight() {
		return posterSize.getHeight();
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getDestPath() {
		return destPath;
	}

	public void setDestPath(String destPath) {
		this.destPath = destPath;
	}

	public PosterCharacter getCharacter() {
		return character;
	}

	public void setCharacter(PosterCharacter character) {
		this.character = character;
	}

	public IFilter getFilter() {
		return filter;
	}

	public void setFilter(IFilter filter) {
		this.filter = filter;
	}

	private int[] getPosterSize() {
		VideoKeyFrameGetter.setVideoWidthAndHeight(sourcePath);
		return new int[] { VideoKeyFrameGetter.getVideoWidth(), VideoKeyFrameGetter.getVideoHeight() };
	}

}
