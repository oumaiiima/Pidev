<?php

namespace App\Repository;

use App\Entity\Gerant;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Gerant>
 *
 * @method Gerant|null find($id, $lockMode = null, $lockVersion = null)
 * @method Gerant|null findOneBy(array $criteria, array $orderBy = null)
 * @method Gerant[]    findAll()
 * @method Gerant[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class GerantRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Gerant::class);
    }

    public function save(Gerant $entity, bool $flush = false): void
    {
        $this->getEntityManager()->persist($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }

    public function remove(Gerant $entity, bool $flush = false): void
    {
        $this->getEntityManager()->remove($entity);

        if ($flush) {
            $this->getEntityManager()->flush();
        }
    }
 





  

}
