import { useState } from "react";
import CardActions from "@mui/material/CardActions";
import IconButton from "@mui/material/IconButton";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import AddDogadajDialog from "./AddDogadajDialog";

export default function EditDelete({ dogadajId }) {
  const [dialogOpen, setDialogOpen] = useState(false);

  function deleteDogadaj() {
    fetch(`/api/dogadaj/delete/${dogadajId}`);
  }

  return (
    <>
      <CardActions disableSpacing>
        <IconButton aria-label="edit" onClick={() => setDialogOpen(true)}>
          <EditIcon />
        </IconButton>
        <IconButton aria-label="delete" onClick={deleteDogadaj}>
          <DeleteIcon />
        </IconButton>
      </CardActions>
      <AddDogadajDialog
        handleClose={() => setDialogOpen(false)}
        open={dialogOpen}
        dogadajId={dogadajId}
      />
    </>
  );
}
