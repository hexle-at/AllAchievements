package at.hexle;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by Hexle
 *
 * Calculate a page for example for an Inventory
 * @param <T> Type/Class
 */
public class Pagination<T> extends ArrayList<T> {

    /**
     * How many T are listed per page
     */
    private final int pageSize;

    /**
     * New Pagination with an empty ArrayList
     * @param pageSize How many Objects are visible per page
     */
    public Pagination(int pageSize) {
        this(pageSize, new ArrayList<>());
    }

    /**
     * New Pagination with  a list
     * @param pageSize How many Objects are visible per page
     * @param objects The objects thar are visible on the page
     */
    public Pagination(int pageSize, List<T> objects) {
        this.pageSize = pageSize;
        addAll(objects);
    }

    public int pageSize() {
        return pageSize;
    }

    /**
     * Get the total pages: calculated from the size and the per page size
     * (ceil) = round always to the next int
     * @return The amount of total pages
     */
    public int totalPages() {
        return (int) Math.ceil((double) size() / pageSize);
    }

    /**
     * Checks if the requested page exists
     * @param page The page number which want to be checked
     * @return true if it exists, otherwise false
     */
    public boolean exists(int page) {
        //return !(page < 0) && page < totalPages();
        return page < 0 || page > totalPages();
    }

    /**
     * Gets a list with the specific item from the list, check first if this page exists!!
     * @param page which page
     * @return Lists with T
     */
    public List<T> getPage(int page) {
        if(page < 0 || page >= totalPages()) throw new IndexOutOfBoundsException("Index: " + page + ", Size: " + totalPages());
        List<T> objects = new ArrayList<>();
        int min = page * pageSize;
        int max = ((page * pageSize) + pageSize);
        if(max > size()) max = size();
        for(int i = min; max > i; i++) {
            objects.add(get(i));
        }
        return objects;
    }
}