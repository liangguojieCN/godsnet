package ${classPath};

import org.nutz.dao.entity.annotation.Table;

@Table("${tableName}")
public class ${className} {

    <#list vmap?keys as mKey>
        private ${vmap["${mKey}"]} ${mKey};
    </#list>

}