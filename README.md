Sitemap
=======

In flux still... but progressing.

TODOs:

 * finish the "partition" support - DONE
 * add protobuf Logger instead the default one (time stability)
 * add DirectBuffer support instead direct FS/inMemory use (lessen heap memory use)
 * transport/remote update/incremental update support
 * separate space4j use into new module, add more backends


Just a scratchpad
-----------------

Sitemap is a small and lean "file registry", that is map-alike and is usually held in
memory, while it is backed by some hard-rock persistence (time prone, upgrade etc).

The main record map is in memory, but these main records might be "decorated" by Contributors.
Contributors contribution are also held in a map-alike structure, but they are lazily loaded
on-demand.

Have fun,  
~t~