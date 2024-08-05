/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.ptronics.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.hybris.ptronics.fulfilmentprocess.constants.PtronicsFulfilmentProcessConstants;

public class PtronicsFulfilmentProcessManager extends GeneratedPtronicsFulfilmentProcessManager
{
	public static final PtronicsFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (PtronicsFulfilmentProcessManager) em.getExtension(PtronicsFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
