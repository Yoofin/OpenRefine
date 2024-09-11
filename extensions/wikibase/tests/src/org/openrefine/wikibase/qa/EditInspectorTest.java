
package org.openrefine.wikibase.qa;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import org.openrefine.wikibase.manifests.Manifest;
import org.openrefine.wikibase.manifests.ManifestParser;
import org.openrefine.wikibase.testing.TestingData;

public class EditInspectorTest {

    private static final int scrutinizerCount = 24;
    private static final int scrutinizerNotDependingOnPropertyConstraintCount = 9;

    @Test
    public void testNoScrutinizerSkipped() throws Exception {
        String manifestJson = TestingData.jsonFromFile("manifest/wikidata-manifest-v1.0.json");
        Manifest manifest = ManifestParser.parse(manifestJson);
        EditInspector editInspector = new EditInspector(new QAWarningStore(), manifest, false);
        assertEquals(editInspector.scrutinizers.size(), scrutinizerCount);
    }

    @Test
    public void toSkipScrutinizerDependingOnConstraintPropertyPid1() throws Exception {
        String manifestJson = TestingData.jsonFromFile("manifest/wikidata-manifest-v1.0-without-constraints.json");
        Manifest manifest = ManifestParser.parse(manifestJson);
        EditInspector editInspector = new EditInspector(new QAWarningStore(), manifest, false);
        assertEquals(editInspector.scrutinizers.size(), scrutinizerNotDependingOnPropertyConstraintCount);
    }

    @Test
    public void toSkipScrutinizerDependingOnConstraintPropertyPid2() throws Exception {
        String manifestJson = TestingData.jsonFromFile("manifest/wikidata-manifest-v1.0-missing-property-constraint-pid.json");
        Manifest manifest = ManifestParser.parse(manifestJson);
        EditInspector editInspector = new EditInspector(new QAWarningStore(), manifest, false);
        assertEquals(editInspector.scrutinizers.size(), scrutinizerNotDependingOnPropertyConstraintCount);
    }
}
