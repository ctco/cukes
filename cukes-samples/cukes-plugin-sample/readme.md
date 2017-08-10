# Cukes Plugins Sample

This is a sample to show how you can build your own plugin.

## Use cases

Plugins are powerful thing that allows you to execute some code before/after all scenarios or features.

Some of the possible usage scenarios might be:

- Clean database before each scenario
- Setup pre-defined entries in database
- Retrieve OAuth token before running all tests

## Implementation

In this sample a simple plugin is implemented (`lv.ctco.cukes.plugins.SamplePlugin`) that just logs how long execution of a scenario took.

Run [showcase.feature](src/test/resources/features/showcase.feature) and check the logs - execution times will be displayed.
 
Please note that the plugin should be enabled explicitly in [cukes.properties](src/test/resources/cukes.properties)
