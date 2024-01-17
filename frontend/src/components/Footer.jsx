import {AppBar, Container, Toolbar, Typography} from '@mui/material';

const Footer = () => {
    return (
        <>
            <footer>
                <AppBar position="fixed" color="default" sx={{
                    top: 'auto',
                    bottom: 0,
                    backgroundColor: 'whitesmoke',
                    height: '80px',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center'}}>
                    <Container maxWidth="xl">
                        <Toolbar sx={{ justifyContent: 'center' }}>
                            <Typography variant="h6" color="inherit">
                                © 2023 EVENTIO. Sva prava pridržana.
                            </Typography>
                        </Toolbar>
                    </Container>
                </AppBar>
            </footer>
        </>
    );
};

export default Footer;
