package com.pws.rctest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author panws
 * @since 2018-07-25
 */
public enum ExtTypeEnum {

	USER("User", 5), DEPT("Dept", 4), AO("AO", 3), TMO("TMO", 2), OTHER("Other", 1);

	private String extType;
	private int priority;

	ExtTypeEnum(String extType, int priority) {
		this.extType = extType;
		this.priority = priority;
	}

	private String getExtType() {
		return extType;
	}

	private int getPriority() {
		return priority;
	}

	/**
	 * recognized extType: return the defined priority
	 * unrecognized extType: return 0
	 *
	 * @param extType
	 * @return
	 */
	public static int priorityOf(String extType) {
		int priority = 0;

		if (StringUtils.isEmpty(extType)) {
			return priority;
		}

		for (ExtTypeEnum extTypeEnum : ExtTypeEnum.values()) {
			if (StringUtils.equals(extTypeEnum.getExtType(), extType)) {
				priority = extTypeEnum.getPriority();
			}
		}
		return priority;
	}
}
