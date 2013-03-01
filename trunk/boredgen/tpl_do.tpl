import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

{% set table = ctx.tables[ctx.t_table] %}
@Entity(name = "{{ table.name }}")
public class {{ table.model_name }} implements Serializable {

    private static final long serialVersionUID = 1L;

    {% for attr in table.model_attributes %}
    {% set f = table.fields[loop.index0] %}

    {% if f.is_pri %}@Id{% endif %}
    @Column(name = "{{ f.name }}")
    private {{ attr.type }} {{ attr.name }};
    {% endfor %}

    {% for f in table.model_attributes %}
    public {{ f.type }} get{{ f.name | capitalizex }}() {
        return {{ f.name }};
    }

    public void set{{ f.name | capitalizex  }}({{ f.type }} {{ f.name }}) {
        this.{{ f.name }} = {{ f.name }};
    }
    {% endfor %}
}
