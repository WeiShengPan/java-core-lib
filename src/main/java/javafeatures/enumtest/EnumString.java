package javafeatures.enumtest;

/**
 * @author panws
 * @since 2017-06-09
 */
public enum EnumString {

	EDGEUP("edgeUp"), EDGEDOWN("edgeDown");

	private final String bandwidthType;

	EnumString(String bandwidthType) {
		this.bandwidthType = bandwidthType;
	}

	public String getBandwidthType() {
		return bandwidthType;
	}
}
