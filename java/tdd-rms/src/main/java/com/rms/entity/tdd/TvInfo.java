package com.rms.entity.tdd;

// Generated 2015-8-26 14:10:45 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TvInfo generated by hbm2java
 */
@Entity
@Table (
    name = "tv_info",
    catalog = "rms")
public class TvInfo implements java.io.Serializable {

	private Integer	           tvId;

	private String	           tvName;

	private int	               tvChannelSubId;

	private Integer	           tvChannelParentId;

	private Integer	           tvUserGroupId;

	private Date	           tvAddtime;

	private int	               tvAdduserId;

	private Integer	           tvModfiyId;

	private Date	           tvModfiytime;

	private Byte	           tvState;

	private String	           tvTimeLengthChar;

	private int	               tvTimeLength;

	private String	           tvUrl;

	private int	               tvProvince;

	private int	               tvCity;

	private String	           tvImage;

	private String	           tvAdConfig;

	private String	           tvFileName;

	private byte	           tvUploadState;

	private Date	           tvUploadTime;

	private int	               tvType;

	private Integer	           tvActivityEnId;

	private Byte	           tvSource;

	private Integer	           tvSort;

	private TvInfoMore	       tvInfoMore;

	private Set<TvRecommended>	tvRecommendeds	= new HashSet<TvRecommended>(0);

	public TvInfo() {

	}

	public TvInfo(String tvName, int tvChannelSubId, Date tvAddtime, int tvAdduserId, int tvTimeLength, String tvUrl, int tvProvince,
	        int tvCity, String tvImage, byte tvUploadState, int tvType) {

		this.tvName = tvName;
		this.tvChannelSubId = tvChannelSubId;
		this.tvAddtime = tvAddtime;
		this.tvAdduserId = tvAdduserId;
		this.tvTimeLength = tvTimeLength;
		this.tvUrl = tvUrl;
		this.tvProvince = tvProvince;
		this.tvCity = tvCity;
		this.tvImage = tvImage;
		this.tvUploadState = tvUploadState;
		this.tvType = tvType;
	}

	public TvInfo(String tvName, int tvChannelSubId, Integer tvChannelParentId, Integer tvUserGroupId, Date tvAddtime, int tvAdduserId,
	        Integer tvModfiyId, Date tvModfiytime, Byte tvState, String tvTimeLengthChar, int tvTimeLength, String tvUrl, int tvProvince,
	        int tvCity, String tvImage, String tvAdConfig, String tvFileName, byte tvUploadState, Date tvUploadTime, int tvType,
	        Integer tvActivityEnId, Byte tvSource, Integer tvSort, TvInfoMore tvInfoMore, Set<TvRecommended> tvRecommendeds) {

		this.tvName = tvName;
		this.tvChannelSubId = tvChannelSubId;
		this.tvChannelParentId = tvChannelParentId;
		this.tvUserGroupId = tvUserGroupId;
		this.tvAddtime = tvAddtime;
		this.tvAdduserId = tvAdduserId;
		this.tvModfiyId = tvModfiyId;
		this.tvModfiytime = tvModfiytime;
		this.tvState = tvState;
		this.tvTimeLengthChar = tvTimeLengthChar;
		this.tvTimeLength = tvTimeLength;
		this.tvUrl = tvUrl;
		this.tvProvince = tvProvince;
		this.tvCity = tvCity;
		this.tvImage = tvImage;
		this.tvAdConfig = tvAdConfig;
		this.tvFileName = tvFileName;
		this.tvUploadState = tvUploadState;
		this.tvUploadTime = tvUploadTime;
		this.tvType = tvType;
		this.tvActivityEnId = tvActivityEnId;
		this.tvSource = tvSource;
		this.tvSort = tvSort;
		this.tvInfoMore = tvInfoMore;
		this.tvRecommendeds = tvRecommendeds;
	}

	@Id
	@GeneratedValue (
	    strategy = IDENTITY)
	@Column (
	    name = "tv_id",
	    unique = true,
	    nullable = false)
	public Integer getTvId() {

		return this.tvId;
	}

	public void setTvId(Integer tvId) {

		this.tvId = tvId;
	}

	@Column (
	    name = "tv_name",
	    nullable = false)
	public String getTvName() {

		return this.tvName;
	}

	public void setTvName(String tvName) {

		this.tvName = tvName;
	}

	@Column (
	    name = "tv_channel_sub_id",
	    nullable = false)
	public int getTvChannelSubId() {

		return this.tvChannelSubId;
	}

	public void setTvChannelSubId(int tvChannelSubId) {

		this.tvChannelSubId = tvChannelSubId;
	}

	@Column (
	    name = "tv_channel_parent_id")
	public Integer getTvChannelParentId() {

		return this.tvChannelParentId;
	}

	public void setTvChannelParentId(Integer tvChannelParentId) {

		this.tvChannelParentId = tvChannelParentId;
	}

	@Column (
	    name = "tv_user_group_id")
	public Integer getTvUserGroupId() {

		return this.tvUserGroupId;
	}

	public void setTvUserGroupId(Integer tvUserGroupId) {

		this.tvUserGroupId = tvUserGroupId;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column (
	    name = "tv_addtime",
	    nullable = false,
	    length = 19)
	public Date getTvAddtime() {

		return this.tvAddtime;
	}

	public void setTvAddtime(Date tvAddtime) {

		this.tvAddtime = tvAddtime;
	}

	@Column (
	    name = "tv_adduser_id",
	    nullable = false)
	public int getTvAdduserId() {

		return this.tvAdduserId;
	}

	public void setTvAdduserId(int tvAdduserId) {

		this.tvAdduserId = tvAdduserId;
	}

	@Column (
	    name = "tv_modfiy_id")
	public Integer getTvModfiyId() {

		return this.tvModfiyId;
	}

	public void setTvModfiyId(Integer tvModfiyId) {

		this.tvModfiyId = tvModfiyId;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column (
	    name = "tv_modfiytime",
	    length = 19)
	public Date getTvModfiytime() {

		return this.tvModfiytime;
	}

	public void setTvModfiytime(Date tvModfiytime) {

		this.tvModfiytime = tvModfiytime;
	}

	@Column (
	    name = "tv_state")
	public Byte getTvState() {

		return this.tvState;
	}

	public void setTvState(Byte tvState) {

		this.tvState = tvState;
	}

	@Column (
	    name = "tv_time_length_char",
	    length = 15)
	public String getTvTimeLengthChar() {

		return this.tvTimeLengthChar;
	}

	public void setTvTimeLengthChar(String tvTimeLengthChar) {

		this.tvTimeLengthChar = tvTimeLengthChar;
	}

	@Column (
	    name = "tv_time_length",
	    nullable = false)
	public int getTvTimeLength() {

		return this.tvTimeLength;
	}

	public void setTvTimeLength(int tvTimeLength) {

		this.tvTimeLength = tvTimeLength;
	}

	@Column (
	    name = "tv_url",
	    nullable = false)
	public String getTvUrl() {

		return this.tvUrl;
	}

	public void setTvUrl(String tvUrl) {

		this.tvUrl = tvUrl;
	}

	@Column (
	    name = "tv_province",
	    nullable = false)
	public int getTvProvince() {

		return this.tvProvince;
	}

	public void setTvProvince(int tvProvince) {

		this.tvProvince = tvProvince;
	}

	@Column (
	    name = "tv_city",
	    nullable = false)
	public int getTvCity() {

		return this.tvCity;
	}

	public void setTvCity(int tvCity) {

		this.tvCity = tvCity;
	}

	@Column (
	    name = "tv_image",
	    nullable = false)
	public String getTvImage() {

		return this.tvImage;
	}

	public void setTvImage(String tvImage) {

		this.tvImage = tvImage;
	}

	@Column (
	    name = "tv_ad_config",
	    length = 30)
	public String getTvAdConfig() {

		return this.tvAdConfig;
	}

	public void setTvAdConfig(String tvAdConfig) {

		this.tvAdConfig = tvAdConfig;
	}

	@Column (
	    name = "tv_file_name")
	public String getTvFileName() {

		return this.tvFileName;
	}

	public void setTvFileName(String tvFileName) {

		this.tvFileName = tvFileName;
	}

	@Column (
	    name = "tv_upload_state",
	    nullable = false)
	public byte getTvUploadState() {

		return this.tvUploadState;
	}

	public void setTvUploadState(byte tvUploadState) {

		this.tvUploadState = tvUploadState;
	}

	@Temporal (TemporalType.TIMESTAMP)
	@Column (
	    name = "tv_upload_time",
	    length = 19)
	public Date getTvUploadTime() {

		return this.tvUploadTime;
	}

	public void setTvUploadTime(Date tvUploadTime) {

		this.tvUploadTime = tvUploadTime;
	}

	@Column (
	    name = "tv_type",
	    nullable = false)
	public int getTvType() {

		return this.tvType;
	}

	public void setTvType(int tvType) {

		this.tvType = tvType;
	}

	@Column (
	    name = "tv_activity_en_id")
	public Integer getTvActivityEnId() {

		return this.tvActivityEnId;
	}

	public void setTvActivityEnId(Integer tvActivityEnId) {

		this.tvActivityEnId = tvActivityEnId;
	}

	@Column (
	    name = "tv_source")
	public Byte getTvSource() {

		return this.tvSource;
	}

	public void setTvSource(Byte tvSource) {

		this.tvSource = tvSource;
	}

	@Column (
	    name = "tv_sort")
	public Integer getTvSort() {

		return this.tvSort;
	}

	public void setTvSort(Integer tvSort) {

		this.tvSort = tvSort;
	}

	@OneToOne (
	    fetch = FetchType.LAZY,
	    mappedBy = "tvInfo")
	public TvInfoMore getTvInfoMore() {

		return this.tvInfoMore;
	}

	public void setTvInfoMore(TvInfoMore tvInfoMore) {

		this.tvInfoMore = tvInfoMore;
	}

	@OneToMany (
	    fetch = FetchType.LAZY,
	    mappedBy = "tvInfo")
	public Set<TvRecommended> getTvRecommendeds() {

		return this.tvRecommendeds;
	}

	public void setTvRecommendeds(Set<TvRecommended> tvRecommendeds) {

		this.tvRecommendeds = tvRecommendeds;
	}

}
