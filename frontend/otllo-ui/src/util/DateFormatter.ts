export default function formatDate(dateString: string): string {
  return new Date(dateString).toLocaleString();
}
