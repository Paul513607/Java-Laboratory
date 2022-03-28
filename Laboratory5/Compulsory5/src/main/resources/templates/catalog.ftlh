<h1> This is the catalog ${name} </h1>

<#list items as item> <br/>
<div>
    Item id: ${item.id} <br/>
    Item title: ${item.title} <br/>
    Item tags: ${item.tags?api.toString()} <br/>
    <#if item.class.simpleName == "Book">
    Item type: book <br/>
</#if>
<#if item.class.simpleName == "Article">
Item type: article <br/>
</#if>
Item year: ${item.publishedYear} <br/>
Item authors: ${item.authors?api.toString()} <br/>
</div>
</#list>