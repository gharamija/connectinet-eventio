import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Checkbox from "@mui/material/Checkbox";
import TableSortLabel from "@mui/material/TableSortLabel";
import Box from "@mui/material/Box";
import {visuallyHidden} from "@mui/utils";

function EnhancedTableHead(props) {
    const { onSelectAllClick, order, orderBy, numSelected, rowCount, onRequestSort, role } =
        props;
    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };
    const headCells = role === "ORGANIZATOR" ? headCellsOrg : headCellsPos;


    return (
        <TableHead>
            <TableRow>
                <TableCell padding="checkbox">
                    <Checkbox
                        color="primary"
                        indeterminate={numSelected > 0 && numSelected < rowCount}
                        checked={rowCount > 0 && numSelected === rowCount}
                        onChange={onSelectAllClick}
                        inputProps={{
                            'aria-label': 'select all desserts',
                        }}
                    />
                </TableCell>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={headCell.numeric ? 'left' : 'right'}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                        sortDirection={orderBy === headCell.id ? order : false}
                    >
                        <TableSortLabel
                            active={orderBy === headCell.id}
                            direction={orderBy === headCell.id ? order : 'asc'}
                            onClick={createSortHandler(headCell.id)}
                        >
                            {headCell.label}
                            {orderBy === headCell.id ? (
                                <Box component="span" sx={visuallyHidden}>
                                    {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                </Box>
                            ) : null}
                        </TableSortLabel>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

const headCellsOrg = [
    {
        id: 'id',
        numeric: true,
        disablePadding: true,
        label: 'ID korisnika',
    },
    {
        id: 'username',
        numeric: false,
        disablePadding: false,
        label: 'Korisničko ime',
    },
    {
        id: 'email',
        numeric: false,
        disablePadding: false,
        label: 'Email',
    },
    {
        id: 'nazivOrganizacije',
        numeric: false,
        disablePadding: false,
        label: 'Naziv organizacije',
    },
    {
        id: 'adresa',
        numeric: false,
        disablePadding: false,
        label: 'Adresa',
    },
    {
        id: 'poveznica',
        numeric: false,
        disablePadding: false,
        label: 'Poveznica',
    },
    {
        id: 'clanarina',
        numeric: false,
        disablePadding: false,
        label: 'Članarina',
    },
];

const headCellsPos = [
    {
        id: 'id',
        numeric: true,
        disablePadding: true,
        label: 'ID korisnika',
    },
    {
        id: 'username',
        numeric: false,
        disablePadding: false,
        label: 'Korisničko ime',
    },
    {
        id: 'email',
        numeric: false,
        disablePadding: false,
        label: 'Email',
    },
];

export default EnhancedTableHead;