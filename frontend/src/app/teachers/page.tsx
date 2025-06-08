export default async function Teachers() {
  const teachers: {
    id: number,
    firstName: string,
    lastName: string,
    prefixTitle: string | undefined,
    suffixTitle: string | undefined,
  }[] = await fetch("http://localhost:3000/api/teacher/list", {cache: "no-store"})
    .then(it => it.json());
  return (
    <table>
      <thead>
      <tr>
        <th>Identifier</th>
        <th>Full Name</th>
      </tr>
      </thead>
      <tbody className="border-2">
      {teachers.map(({id, firstName, lastName, prefixTitle, suffixTitle}) => <tr key={id}>
        <td>{id}</td>
        <td>{prefixTitle && `${prefixTitle} `}{firstName} {lastName}{suffixTitle && ` ${suffixTitle}`}</td>
      </tr>)}
      </tbody>
    </table>
  )
}
