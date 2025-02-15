<p>
All modules use the same database: "hibernate-associations", but different schemas.<br/>
So, create the following schemas in the database if they don't yet exist:
</p>

- one_to_one_unidirectional
- one_to_one_bidirectional

- many_to_one_unidirectional
- many_to_one_bidirectional

- one_to_many_unidirectional

- many_to_many_unidirectional
- many_to_many_bidirectional
- many_to_many_custom

<p>
P.S. the database and schemas can be completely empty, since in all examples the "hbm2ddl.auto" property is set to "create".
</p>