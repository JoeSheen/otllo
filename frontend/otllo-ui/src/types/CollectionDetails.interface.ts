export interface CollectionDetails<T> {
  details: T[];
  currentPage: number;
  totalPages: number;
  totalElements: number;
}
