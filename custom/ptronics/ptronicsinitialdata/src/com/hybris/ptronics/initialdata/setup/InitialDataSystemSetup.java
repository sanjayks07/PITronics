/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package com.hybris.ptronics.initialdata.setup;

import com.hybris.ptronics.initialdata.services.dataimport.impl.PTronicsSampleDataImportService;
import de.hybris.platform.commerceservices.dataimport.impl.CoreDataImportService;
import de.hybris.platform.commerceservices.setup.AbstractSystemSetup;
import de.hybris.platform.commerceservices.setup.data.ImportData;
import de.hybris.platform.commerceservices.setup.events.CoreDataImportedEvent;
import de.hybris.platform.commerceservices.setup.events.SampleDataImportedEvent;
import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetup.Process;
import de.hybris.platform.core.initialization.SystemSetup.Type;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import com.hybris.ptronics.initialdata.constants.PTronicsInitialDataConstants;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


/**
 * This class provides hooks into the system's initialization and update processes.
 */
@SystemSetup(extension = PTronicsInitialDataConstants.EXTENSIONNAME)
public class InitialDataSystemSetup extends AbstractSystemSetup {
    public static final String PTRONICS = "ptronics";
    public static final String PTRONICS_STANDALONE = "ptronics-standalone";
    private static final String IMPORT_CORE_DATA = "importCoreData";
    private static final String IMPORT_SAMPLE_DATA = "importSampleData";
    private static final String ACTIVATE_SOLR_CRON_JOBS = "activateSolrCronJobs";

    private CoreDataImportService coreDataImportService;
    private PTronicsSampleDataImportService pTronicsSampleDataImportService;

    /**
     * Generates the Dropdown and Multi-select boxes for the project data import
     */
    @Override
    @SystemSetupParameterMethod
    public List<SystemSetupParameter> getInitializationOptions() {
        final List<SystemSetupParameter> params = new ArrayList<SystemSetupParameter>();

        params.add(createBooleanSystemSetupParameter(IMPORT_CORE_DATA, "Import Core Data", true));
        params.add(createBooleanSystemSetupParameter(IMPORT_SAMPLE_DATA, "Import Sample Data", true));
        params.add(createBooleanSystemSetupParameter(ACTIVATE_SOLR_CRON_JOBS, "Activate Solr Cron Jobs", true));
        // Add more Parameters here as you require

        return params;
    }

    /**
     * Implement this method to create initial objects. This method will be called by system creator during
     * initialization and system update. Be sure that this method can be called repeatedly.
     *
     * @param context the context provides the selected parameters and values
     */
    @SystemSetup(type = Type.ESSENTIAL, process = Process.ALL)
    public void createEssentialData(final SystemSetupContext context) {
        // Add Essential Data here as you require
    }

    /**
     * Implement this method to create data that is used in your project. This method will be called during the system
     * initialization. <br>
     * Add import data for each site you have configured
     *
     * <pre>
     * final List<ImportData> importData = new ArrayList<ImportData>();
     *
     * final ImportData sampleImportData = new ImportData();
     * sampleImportData.setProductCatalogName(SAMPLE_PRODUCT_CATALOG_NAME);
     * sampleImportData.setContentCatalogNames(Arrays.asList(SAMPLE_CONTENT_CATALOG_NAME));
     * sampleImportData.setStoreNames(Arrays.asList(SAMPLE_STORE_NAME));
     * importData.add(sampleImportData);
     *
     * getCoreDataImportService().execute(this, context, importData);
     * getEventService().publishEvent(new CoreDataImportedEvent(context, importData));
     *
     * getSampleDataImportService().execute(this, context, importData);
     * getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
     * </pre>
     *
     * @param context the context provides the selected parameters and values
     */
    @SystemSetup(type = Type.PROJECT, process = Process.ALL)
    public void createProjectData(final SystemSetupContext context) {
        final List<ImportData> importData = new ArrayList<ImportData>();

        final ImportData powertoolsImportData = new ImportData();
        powertoolsImportData.setProductCatalogName(PTRONICS);
        powertoolsImportData.setContentCatalogNames(List.of(PTRONICS));
        powertoolsImportData.setStoreNames(List.of(PTRONICS));
        powertoolsImportData.setSiteNames(List.of(PTRONICS_STANDALONE));
        importData.add(powertoolsImportData);

        getCoreDataImportService().execute(this, context, importData);
        getEventService().publishEvent(new CoreDataImportedEvent(context, importData));

        getpTronicsSampleDataImportService().execute(this, context, importData);
        getpTronicsSampleDataImportService().importCommerceOrgData(context);
        getEventService().publishEvent(new SampleDataImportedEvent(context, importData));
    }

    public CoreDataImportService getCoreDataImportService() {
        return coreDataImportService;
    }

    @Required
    public void setCoreDataImportService(final CoreDataImportService coreDataImportService) {
        this.coreDataImportService = coreDataImportService;
    }

    public PTronicsSampleDataImportService getpTronicsSampleDataImportService() {
        return pTronicsSampleDataImportService;
    }

    @Required
    public void setpTronicsSampleDataImportService(PTronicsSampleDataImportService pTronicsSampleDataImportService) {
        this.pTronicsSampleDataImportService = pTronicsSampleDataImportService;
    }
}
