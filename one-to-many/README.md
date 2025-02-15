<p>
<strong>Bidirectional</strong> is absent here, because it is the same as in <strong>"many-to-one"</strong>.
</p>

<p>
<strong>Unidirectional</strong> is separately interesting because when it is not mirrored by <strong>@ManyToOne</strong>,
Hibernate resorts to creating a "link" table between the two joining entities (the same as for <strong>Many-To-Many</strong> association),
instead of changing the SQL structure of the logical "owner" entity.
</p>
<p>
This may be needed when a collection is conceptually part of an entity as one of its properties,
but is extracted into another entity and linked by an association only because SQL cannot store collections in columns.
</p>