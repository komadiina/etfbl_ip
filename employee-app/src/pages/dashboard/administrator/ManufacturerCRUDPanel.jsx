import ManufacturerEntryForm from "../../../components/ManufacturerEntryForm";
import ManufacturerTableGeneric from "../../../components/tables/ManufacturerTableGeneric";

export default function ManufacturerCRUDPanel() {
  return (
    <div className="flex flex-col">
      <ManufacturerTableGeneric />

      <hr className="mt-2 mb-4" />

      <ManufacturerEntryForm />
    </div>
  )
}
