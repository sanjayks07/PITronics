/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.ptronics.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import com.hybris.ptronics.core.constants.PtronicsCoreConstants;
import com.hybris.ptronics.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class PtronicsCoreManager extends GeneratedPtronicsCoreManager
{
	public static final PtronicsCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (PtronicsCoreManager) em.getExtension(PtronicsCoreConstants.EXTENSIONNAME);
	}
}
