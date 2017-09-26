package lv.ctco.cukes.rest.loadrunner.function;

/*
Utility C function to generate random string of specified length
 */
public class InitializeGenerateRandomStringFunction implements LoadRunnerFunction {

    @Override
    public String format() {
        return "char *lr_guid_gen()\n" +
            "{\n" +
            "    typedef struct _GUID\n" +
            "    {\n" +
            "        unsigned long Data1;\n" +
            "        unsigned short Data2;\n" +
            "        unsigned short Data3;\n" +
            "        unsigned char Data4[4];\n" +
            "    } GUID;\n" +
            "    char* guid;\n" +
            "    GUID m_guid;\n" +
            "    guid = (char*)malloc(50);\n" +
            "    memset(guid, 0, 50);\n" +
            "    lr_load_dll (\"ole32.dll\");\n" +
            "    CoCreateGuid(&m_guid);\n" +
            "    sprintf (guid, \"%08lx-%04x-%04x-%02x%02x-%02x%02x%02x%02x%02x%02x\",\n" +
            "    m_guid.Data1, m_guid.Data2, m_guid.Data3,\n" +
            "    m_guid.Data4[0], m_guid.Data4[1], m_guid.Data4[2], m_guid.Data4[3],\n" +
            "    m_guid.Data4[4], m_guid.Data4[5], m_guid.Data4[6], m_guid.Data4[7]);\n" +
            "    return guid;\n" +
            "}\n";
    }
}
